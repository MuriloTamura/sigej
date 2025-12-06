package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.TipoMovimentoEstoque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoMovimentoEstoqueDAO {

    public void inserir(TipoMovimentoEstoque t) {
        String sql = "INSERT INTO tipo_movimento_estoque (descricao, sinal) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.setString(2, String.valueOf(t.getSinal()));

            stmt.executeUpdate();
            System.out.println("Tipo de movimento inserido!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir tipo_movimento_estoque: " + e.getMessage());
        }
    }

    public List<TipoMovimentoEstoque> listar() {
        List<TipoMovimentoEstoque> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_movimento_estoque ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new TipoMovimentoEstoque(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getString("sinal").charAt(0)
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar tipo_movimento_estoque: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(TipoMovimentoEstoque t) {
        String sql = "UPDATE tipo_movimento_estoque SET descricao = ?, sinal = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.setString(2, String.valueOf(t.getSinal()));
            stmt.setInt(3, t.getId());

            stmt.executeUpdate();
            System.out.println("Tipo de movimento atualizado!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar tipo_movimento_estoque: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tipo_movimento_estoque WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Tipo de movimento removido!");

        } catch (SQLException e) {

            if (e.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem movimentos de estoque associados a este tipo.");
            } else {
                System.out.println("Erro ao deletar tipo_movimento_estoque: " + e.getMessage());
            }
        }
    }
}
