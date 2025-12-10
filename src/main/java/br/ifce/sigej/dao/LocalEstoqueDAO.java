package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.LocalEstoque;
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
        String sql = "SELECT * FROM local_estoque ORDER BY descricao";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearLocalEstoque(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar locais de estoque: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<LocalEstoque> buscarPorId(int id) {
        String sql = "SELECT * FROM local_estoque WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearLocalEstoque(rs));
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

    private LocalEstoque mapearLocalEstoque(ResultSet rs) throws SQLException {
        LocalEstoque l = new LocalEstoque();
        l.setId(rs.getInt("id"));
        l.setDescricao(rs.getString("descricao"));
        l.setResponsavelId((Integer) rs.getObject("responsavel_id"));
        return l;
    }
}