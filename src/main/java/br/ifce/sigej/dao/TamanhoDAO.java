package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Tamanho;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TamanhoDAO {

    public void inserir(Tamanho t) {
        String sql = "INSERT INTO tamanho (descricao) VALUES (?)";

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
        String sql = "SELECT * FROM tamanho ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Tamanho(
                        rs.getInt("id"),
                        rs.getString("descricao")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar tamanho: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(Tamanho t) {
        String sql = "UPDATE tamanho SET descricao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.setInt(2, t.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar tamanho: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tamanho WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {

            if (ex.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem variações de produto usando este tamanho.");
            } else {
                System.out.println("Erro ao deletar tamanho: " + ex.getMessage());
            }
        }
    }
}
