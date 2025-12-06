package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.LocalEstoque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalEstoqueDAO {

    public void inserir(LocalEstoque l) {
        String sql = "INSERT INTO local_estoque (descricao, responsavel_id) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getDescricao());
            stmt.setObject(2, l.getResponsavelId());

            stmt.executeUpdate();
            System.out.println("Local de estoque inserido!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir local_estoque: " + e.getMessage());
        }
    }

    public List<LocalEstoque> listar() {
        List<LocalEstoque> lista = new ArrayList<>();
        String sql = "SELECT * FROM local_estoque ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new LocalEstoque(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        (Integer) rs.getObject("responsavel_id")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar local_estoque: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(LocalEstoque l) {
        String sql = "UPDATE local_estoque SET descricao = ?, responsavel_id = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getDescricao());
            stmt.setObject(2, l.getResponsavelId());
            stmt.setInt(3, l.getId());

            stmt.executeUpdate();
            System.out.println("Local de estoque atualizado!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar local_estoque: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM local_estoque WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Local de estoque removido!");

        } catch (SQLException e) {

            if (e.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem produtos em estoque ou movimentações associadas a este local.");
            } else {
                System.out.println("Erro ao deletar local_estoque: " + e.getMessage());
            }
        }
    }
}
