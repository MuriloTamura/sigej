package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.AreaCampus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AreaCampusDAO {

    public void inserir(AreaCampus a) {
        String sql = "INSERT INTO area_campus (tipo_area_id, descricao, bloco) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getTipoAreaId());
            stmt.setString(2, a.getDescricao());
            stmt.setString(3, a.getBloco());

            stmt.executeUpdate();
            System.out.println("Área do campus inserida!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir area_campus: " + e.getMessage());
        }
    }

    public List<AreaCampus> listar() {
        List<AreaCampus> lista = new ArrayList<>();
        String sql = "SELECT * FROM area_campus ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new AreaCampus(
                        rs.getInt("id"),
                        rs.getInt("tipo_area_id"),
                        rs.getString("descricao"),
                        rs.getString("bloco")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar area_campus: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(AreaCampus a) {
        String sql = "UPDATE area_campus SET tipo_area_id = ?, descricao = ?, bloco = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getTipoAreaId());
            stmt.setString(2, a.getDescricao());
            stmt.setString(3, a.getBloco());
            stmt.setInt(4, a.getId());

            stmt.executeUpdate();
            System.out.println("Área do campus atualizada!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar area_campus: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM area_campus WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Área do campus deletada!");

        } catch (SQLException e) {

            if (e.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existe ordem_servico usando essa área!");
            } else {
                System.out.println("Erro ao deletar area_campus: " + e.getMessage());
            }
        }
    }
}
