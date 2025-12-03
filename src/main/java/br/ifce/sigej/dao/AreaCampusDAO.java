package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.AreaCampus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AreaCampusDAO {

    public void inserir(AreaCampus area) {
        String sql = "INSERT INTO area_campus (tipo_area_id, descricao, bloco) VALUES (?, ?, ?);";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, area.getTipoAreaId());
            stmt.setString(2, area.getDescricao());
            stmt.setString(3, area.getBloco());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir area_campus: " + e.getMessage());
        }
    }

    public List<AreaCampus> listar() {
        List<AreaCampus> lista = new ArrayList<>();
        String sql = "SELECT * FROM area_campus ORDER BY id;";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AreaCampus area = new AreaCampus();
                area.setId(rs.getInt("id"));
                area.setTipoAreaId((Integer) rs.getObject("tipo_area_id"));
                area.setDescricao(rs.getString("descricao"));
                area.setBloco(rs.getString("bloco"));
                lista.add(area);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar area_campus: " + e.getMessage());
        }

        return lista;
    }
}
