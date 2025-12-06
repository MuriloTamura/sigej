package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Estoque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {

    public void inserir(Estoque e) {
        String sql = """
            INSERT INTO estoque (produto_variacao_id, local_estoque_id, quantidade, ponto_reposicao)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, e.getProdutoVariacaoId());
            stmt.setInt(2, e.getLocalEstoqueId());
            stmt.setDouble(3, e.getQuantidade());
            stmt.setDouble(4, e.getPontoReposicao());

            stmt.executeUpdate();
            System.out.println("Item de estoque inserido!");

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir estoque: " + ex.getMessage());
        }
    }

    public List<Estoque> listar() {
        List<Estoque> lista = new ArrayList<>();
        String sql = "SELECT * FROM estoque ORDER BY produto_variacao_id, local_estoque_id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Estoque e = new Estoque(
                        rs.getInt("produto_variacao_id"),
                        rs.getInt("local_estoque_id"),
                        rs.getDouble("quantidade"),
                        rs.getDouble("ponto_reposicao")
                );
                lista.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao listar estoque: " + ex.getMessage());
        }

        return lista;
    }

    public void atualizar(Estoque e) {
        String sql = """
            UPDATE estoque
            SET quantidade = ?, ponto_reposicao = ?
            WHERE produto_variacao_id = ? AND local_estoque_id = ?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, e.getQuantidade());
            stmt.setDouble(2, e.getPontoReposicao());
            stmt.setInt(3, e.getProdutoVariacaoId());
            stmt.setInt(4, e.getLocalEstoqueId());

            stmt.executeUpdate();
            System.out.println("Estoque atualizado!");

        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar estoque: " + ex.getMessage());
        }
    }

    public void deletar(int produtoVariacaoId, int localEstoqueId) {
        String sql = "DELETE FROM estoque WHERE produto_variacao_id = ? AND local_estoque_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produtoVariacaoId);
            stmt.setInt(2, localEstoqueId);

            stmt.executeUpdate();
            System.out.println("Item de estoque removido!");

        } catch (SQLException ex) {
            if (ex.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem movimentações associadas a esse estoque.");
            } else {
                System.out.println("Erro ao deletar estoque: " + ex.getMessage());
            }
        }
    }
}
