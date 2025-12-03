package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Tamanho;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TamanhoDAO {

    public void inserir(Tamanho t) {
        String sql = "INSERT INTO tamanho (descricao) VALUES (?);";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir tamanho: " + e.getMessage());
        }
    }

    public List<Tamanho> listar() {
        List<Tamanho> lista = new ArrayList<>();
        String sql = "SELECT * FROM tamanho ORDER BY id;";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tamanho t = new Tamanho();
                t.setId(rs.getInt("id"));
                t.setDescricao(rs.getString("descricao"));
                lista.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar tamanho: " + e.getMessage());
        }

        return lista;
    }
}
