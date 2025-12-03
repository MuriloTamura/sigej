package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.TipoAreaCampus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoAreaCampusDAO {

    public void inserir(TipoAreaCampus tipo) {
        String sql = "INSERT INTO tipo_area_campus (descricao) VALUES (?);";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo.getDescricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir tipo_area_campus: " + e.getMessage());
        }
    }

    public List<TipoAreaCampus> listar() {
        List<TipoAreaCampus> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_area_campus ORDER BY id;";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TipoAreaCampus tipo = new TipoAreaCampus();
                tipo.setId(rs.getInt("id"));
                tipo.setDescricao(rs.getString("descricao"));
                lista.add(tipo);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar tipo_area_campus: " + e.getMessage());
        }

        return lista;
    }
}
