package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.TipoAreaCampus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoAreaCampusDAO {

    public void inserir(TipoAreaCampus t) {
        String sql = "INSERT INTO tipo_area_campus (descricao) VALUES (?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.executeUpdate();

            System.out.println("TipoÁrea inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir tipo_area_campus: " + e.getMessage());
        }
    }

    public List<TipoAreaCampus> listar() {
        List<TipoAreaCampus> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_area_campus ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new TipoAreaCampus(
                        rs.getInt("id"),
                        rs.getString("descricao")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar tipo_area_campus: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(TipoAreaCampus t) {
        String sql = "UPDATE tipo_area_campus SET descricao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.setInt(2, t.getId());

            stmt.executeUpdate();

            System.out.println("TipoÁrea atualizado!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar tipo_area_campus: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tipo_area_campus WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("TipoÁrea deletado!");

        } catch (SQLException e) {

            if (e.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existe área_campus usando esse tipo!");
            } else {
                System.out.println("Erro ao deletar tipo_area_campus: " + e.getMessage());
            }
        }
    }
}
