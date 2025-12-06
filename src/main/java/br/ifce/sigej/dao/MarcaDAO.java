package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Marca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {

    public void inserir(Marca m) {
        String sql = "INSERT INTO marca (nome) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir marca: " + e.getMessage());
        }
    }

    public List<Marca> listar() {
        List<Marca> lista = new ArrayList<>();
        String sql = "SELECT * FROM marca ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Marca(
                        rs.getInt("id"),
                        rs.getString("nome")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar marca: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(Marca m) {
        String sql = "UPDATE marca SET nome = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getNome());
            stmt.setInt(2, m.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar marca: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM marca WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {

            if (ex.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem produtos vinculados a esta marca.");
            } else {
                System.out.println("Erro ao deletar marca: " + ex.getMessage());
            }
        }
    }
}
