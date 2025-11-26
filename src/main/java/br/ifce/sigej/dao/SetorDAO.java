package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Setor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SetorDAO {

    public void inserir(Setor s) {
        String sql = "INSERT INTO setor (nome, sigla) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getNome());
            stmt.setString(2, s.getSigla());
            stmt.executeUpdate();

            System.out.println("Setor inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir setor: " + e.getMessage());
        }
    }


    public List<Setor> listar() {
        List<Setor> lista = new ArrayList<>();
        String sql = "SELECT * FROM setor ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Setor s = new Setor();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setSigla(rs.getString("sigla"));
                lista.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar setores: " + e.getMessage());
        }

        return lista;
    }
}
