package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Estoque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {

    public void inserir(Estoque e) {
        String sql = "INSERT INTO estoque (produto_variacao_id, local_estoque_id, quantidade, ponto_reposicao) " +
                "VALUES (?, ?, ?, ?);";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, e.getProdutoVariacaoId());
            stmt.setObject(2, e.getLocalEstoqueId());
            stmt.setObject(3, e.getQuantidade());
            stmt.setObject(4, e.getPontoReposicao());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir estoque: " + ex.getMessage());
        }
    }

    public List<Estoque> listar() {
        List<Estoque> lista = new ArrayList<>();
        String sql = "SELECT * FROM estoque ORDER BY produto_variacao_id, local_estoque_id;";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Estoque e = new Estoque();
                e.setProdutoVariacaoId((Integer) rs.getObject("produto_variacao_id"));
                e.setLocalEstoqueId((Integer) rs.getObject("local_estoque_id"));
                e.setQuantidade(rs.getDouble("quantidade"));
                e.setPontoReposicao(rs.getDouble("ponto_reposicao"));
                lista.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao listar estoque: " + ex.getMessage());
        }

        return lista;
    }
}
