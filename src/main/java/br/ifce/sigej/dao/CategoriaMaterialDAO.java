package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.CategoriaMaterial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaMaterialDAO {

    public void inserir(CategoriaMaterial categoria) {
        String sql = "INSERT INTO categoria_material (nome) VALUES (?);";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir categoria_material: " + e.getMessage());
        }
    }

    public List<CategoriaMaterial> listar() {
        List<CategoriaMaterial> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria_material ORDER BY id;";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CategoriaMaterial cat = new CategoriaMaterial();
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
                lista.add(cat);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar categoria_material: " + e.getMessage());
        }


        return lista;
    }
    public void atualizar(CategoriaMaterial c) {
        String sql = "UPDATE categoria_material SET nome=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar categoria_material: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM categoria_material WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao deletar categoria_material: " + e.getMessage());
        }
    }

}
