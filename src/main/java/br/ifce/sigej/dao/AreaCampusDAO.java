package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.AreaCampus;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AreaCampusDAO {

    public void inserir(AreaCampus a) {
        String sql = "INSERT INTO area_campus (tipo_area_id, descricao, bloco) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getTipoAreaId());
            stmt.setString(2, a.getDescricao());
            stmt.setString(3, a.getBloco());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir área do campus: " + e.getMessage(), e);
        }
    }

    public List<AreaCampus> listar() {
        List<AreaCampus> lista = new ArrayList<>();
        String sql = """
            SELECT ac.*, 
                   ta.descricao AS tipo_area_descricao
            FROM area_campus ac
            LEFT JOIN tipo_area_campus ta ON ac.tipo_area_id = ta.id
            ORDER BY ac.id
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearAreaCampusComTipo(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar áreas do campus: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<AreaCampus> buscarPorId(int id) {
        String sql = """
            SELECT ac.*, 
                   ta.descricao AS tipo_area_descricao
            FROM area_campus ac
            LEFT JOIN tipo_area_campus ta ON ac.tipo_area_id = ta.id
            WHERE ac.id = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearAreaCampusComTipo(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar área do campus: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(AreaCampus a) {
        String sql = """
        UPDATE area_campus 
        SET tipo_area_id=?, descricao=?, bloco=? 
        WHERE id=?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getTipoAreaId());
            stmt.setString(2, a.getDescricao());
            stmt.setString(3, a.getBloco());
            stmt.setInt(4, a.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar área do campus: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM area_campus WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: esta área está sendo referenciada em outras tabelas.");
            }
            throw new RuntimeException("Erro ao deletar área do campus: " + e.getMessage(), e);
        }
    }

    private AreaCampus mapearAreaCampusComTipo(ResultSet rs) throws SQLException {
        AreaCampus a = new AreaCampus();
        a.setId(rs.getInt("id"));

        // Tratamento para evitar null
        int tipoAreaIdValue = rs.getInt("tipo_area_id");
        if (!rs.wasNull()) {
            a.setTipoAreaId(tipoAreaIdValue);
        }

        a.setDescricao(rs.getString("descricao"));
        a.setBloco(rs.getString("bloco"));

        // Campo extra para exibição
        a.setTipoAreaDescricao(rs.getString("tipo_area_descricao"));

        return a;
    }
}