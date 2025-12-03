package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Marca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {

    public void inserir(Marca marca) {
        String sql = "INSERT INTO marca (nome) VALUES (?);";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, marca.getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir marca: " + e.getMessage());
        }
    }

    public List<Marca> listar() {
        List<Marca> lista = new ArrayList<>();
        String sql = "SELECT * FROM marca ORDER BY id;";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Marca m = new Marca();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                lista.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar marca: " + e.getMessage());
        }

        return lista;
    }
}
