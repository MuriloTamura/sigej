package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.LocalEstoque;
import br.ifce.sigej.model.Funcionario;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class LocalEstoqueDAO {

    public void inserir(LocalEstoque l) {
        String sql = "INSERT INTO local_estoque (descricao, responsavel_id) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getDescricao());
            stmt.setObject(2, l.getResponsavelId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir local de estoque: " + e.getMessage(), e);
        }
    }

    public List<LocalEstoque> listar() {
        List<LocalEstoque> lista = new ArrayList<>();
        // JOIN duplo: local_estoque -> funcionario -> pessoa
        String sql = "SELECT le.id, le.descricao, le.responsavel_id, " +
                "f.id as funcionario_id, p.nome as pessoa_nome " +
                "FROM local_estoque le " +
                "LEFT JOIN funcionario f ON le.responsavel_id = f.id " +
                "LEFT JOIN pessoa p ON f.pessoa_id = p.id " +
                "ORDER BY le.descricao";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LocalEstoque local = new LocalEstoque();
                local.setId(rs.getInt("id"));
                local.setDescricao(rs.getString("descricao"));
                local.setResponsavelId((Integer) rs.getObject("responsavel_id"));

                // Se existe funcionário, criar objeto Funcionario com o nome da pessoa
                String nomePessoa = rs.getString("pessoa_nome");
                if (nomePessoa != null && !nomePessoa.trim().isEmpty()) {
                    Funcionario responsavel = new Funcionario();
                    responsavel.setId(rs.getInt("funcionario_id"));
                    responsavel.setPessoaNome(nomePessoa);  // Usa o campo extra pessoaNome
                    local.setResponsavel(responsavel);
                }

                lista.add(local);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar locais de estoque: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<LocalEstoque> buscarPorId(int id) {
        String sql = "SELECT le.id, le.descricao, le.responsavel_id, " +
                "f.id as funcionario_id, p.nome as pessoa_nome " +
                "FROM local_estoque le " +
                "LEFT JOIN funcionario f ON le.responsavel_id = f.id " +
                "LEFT JOIN pessoa p ON f.pessoa_id = p.id " +
                "WHERE le.id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LocalEstoque local = new LocalEstoque();
                local.setId(rs.getInt("id"));
                local.setDescricao(rs.getString("descricao"));
                local.setResponsavelId((Integer) rs.getObject("responsavel_id"));

                String nomePessoa = rs.getString("pessoa_nome");
                if (nomePessoa != null && !nomePessoa.trim().isEmpty()) {
                    Funcionario responsavel = new Funcionario();
                    responsavel.setId(rs.getInt("funcionario_id"));
                    responsavel.setPessoaNome(nomePessoa);
                    local.setResponsavel(responsavel);
                }

                return Optional.of(local);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar local de estoque: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(LocalEstoque l) {
        String sql = "UPDATE local_estoque SET descricao=?, responsavel_id=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getDescricao());
            stmt.setObject(2, l.getResponsavelId());
            stmt.setInt(3, l.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar local de estoque: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM local_estoque WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: existem produtos em estoque ou movimentações associadas a este local.");
            }
            throw new RuntimeException("Erro ao deletar local de estoque: " + e.getMessage(), e);
        }
    }
}