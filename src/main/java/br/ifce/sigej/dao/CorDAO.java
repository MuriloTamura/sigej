package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Cor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CorDAO {

    public void inserir(Cor c) {
        String sql = "INSERT INTO cor (nome) VALUES (?);";

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
        String sql = "SELECT * FROM cor ORDER BY id;";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cor c = new Cor();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar cor: " + e.getMessage());
        }

        return lista;
    }
}
