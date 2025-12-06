package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.TipoFuncionario;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TipoFuncionarioDAO {

    public void inserir(TipoFuncionario t) {
        String sql = "INSERT INTO tipo_funcionario (descricao) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir tipo de funcionário: " + e.getMessage(), e);
        }
    }

    public List<TipoFuncionario> listar() {
        List<TipoFuncionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_funcionario ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new TipoFuncionario(
                        rs.getInt("id"),
                        rs.getString("descricao")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tipos de funcionário: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<TipoFuncionario> buscarPorId(int id) {
        String sql = "SELECT * FROM tipo_funcionario WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new TipoFuncionario(
                        rs.getInt("id"),
                        rs.getString("descricao")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tipo de funcionário: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(TipoFuncionario t) {
        String sql = "UPDATE tipo_funcionario SET descricao=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.setInt(2, t.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tipo de funcionário: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tipo_funcionario WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: este tipo de funcionário está sendo usado.");
            }
            throw new RuntimeException("Erro ao deletar tipo de funcionário: " + e.getMessage(), e);
        }
    }
}
