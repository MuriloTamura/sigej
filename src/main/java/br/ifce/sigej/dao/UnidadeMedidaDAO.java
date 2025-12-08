package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.UnidadeMedida;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UnidadeMedidaDAO {

    public void inserir(UnidadeMedida u) {
        String sql = "INSERT INTO unidade_medida (sigla, descricao) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getSigla());
            stmt.setString(2, u.getDescricao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir unidade de medida: " + e.getMessage(), e);
        }
    }

    public List<UnidadeMedida> listar() {
        List<UnidadeMedida> lista = new ArrayList<>();
        String sql = "SELECT * FROM unidade_medida ORDER BY sigla";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new UnidadeMedida(
                        rs.getInt("id"),
                        rs.getString("sigla"),
                        rs.getString("descricao")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar unidades de medida: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<UnidadeMedida> buscarPorId(int id) {
        String sql = "SELECT * FROM unidade_medida WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new UnidadeMedida(
                        rs.getInt("id"),
                        rs.getString("sigla"),
                        rs.getString("descricao")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar unidade de medida: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(UnidadeMedida u) {
        String sql = "UPDATE unidade_medida SET sigla = ?, descricao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getSigla());
            stmt.setString(2, u.getDescricao());
            stmt.setInt(3, u.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar unidade de medida: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM unidade_medida WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getMessage().contains("foreign key")) {
                throw new RuntimeException("Não é possível excluir: existem produtos vinculados a esta unidade de medida.");
            }
            throw new RuntimeException("Erro ao deletar unidade de medida: " + ex.getMessage(), ex);
        }
    }
}