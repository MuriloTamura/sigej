package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.TipoAreaCampus;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TipoAreaCampusDAO {

    public void inserir(TipoAreaCampus t) {
        String sql = "INSERT INTO tipo_area_campus (descricao) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir tipo de área do campus: " + e.getMessage(), e);
        }
    }

    public List<TipoAreaCampus> listar() {
        List<TipoAreaCampus> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_area_campus ORDER BY descricao";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearTipoAreaCampus(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tipos de área do campus: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<TipoAreaCampus> buscarPorId(int id) {
        String sql = "SELECT * FROM tipo_area_campus WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearTipoAreaCampus(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tipo de área do campus: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(TipoAreaCampus t) {
        String sql = "UPDATE tipo_area_campus SET descricao=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.setInt(2, t.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tipo de área do campus: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tipo_area_campus WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: este tipo está sendo usado em áreas do campus.");
            }
            throw new RuntimeException("Erro ao deletar tipo de área do campus: " + e.getMessage(), e);
        }
    }

    private TipoAreaCampus mapearTipoAreaCampus(ResultSet rs) throws SQLException {
        TipoAreaCampus t = new TipoAreaCampus();
        t.setId(rs.getInt("id"));
        t.setDescricao(rs.getString("descricao"));
        return t;
    }
}