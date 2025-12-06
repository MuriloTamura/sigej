package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Cor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CorDAO {

    public void inserir(Cor c) {
        String sql = "INSERT INTO cor (nome) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir cor: " + e.getMessage());
        }
    }

    public List<Cor> listar() {
        List<Cor> lista = new ArrayList<>();
        String sql = "SELECT * FROM cor ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Cor(
                        rs.getInt("id"),
                        rs.getString("nome")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar cor: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(Cor c) {
        String sql = "UPDATE cor SET nome = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cor: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM cor WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {

            if (ex.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem variações de produto usando esta cor.");
            } else {
                System.out.println("Erro ao deletar cor: " + ex.getMessage());
            }
        }
    }
}
