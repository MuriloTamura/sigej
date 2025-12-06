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

        } catch (SQLException e) {
            System.out.println("Erro ao inserir setor: " + e.getMessage());
        }
    }

    public List<Setor> listar() {
        List<Setor> lista = new ArrayList<>();
        String sql = "SELECT * FROM setor ORDER BY id;";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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

    public void atualizar(Setor s) {
        String sql = "UPDATE setor SET nome=?, sigla=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getNome());
            stmt.setString(2, s.getSigla());
            stmt.setInt(3, s.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar setor: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM setor WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                System.out.println("Não é possível excluir: setor está sendo usado em funcionário.");
            } else {
                System.out.println("Erro ao deletar setor: " + e.getMessage());
            }
        }
    }
}
