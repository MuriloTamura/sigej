package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.ProdutoVariacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoVariacaoDAO {

    public void inserir(ProdutoVariacao p) {
        String sql = """
            INSERT INTO produto_variacao (
                produto_id, cor_id, tamanho_id, codigo_barras, codigo_interno
            ) VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, p.getProdutoId());
            stmt.setObject(2, p.getCorId());
            stmt.setObject(3, p.getTamanhoId());
            stmt.setString(4, p.getCodigoBarras());
            stmt.setString(5, p.getCodigoInterno());

            stmt.executeUpdate();
            System.out.println("Produto variação inserido!");
        } catch (Exception e) {
            System.out.println("Erro ao inserir produto variação: " + e.getMessage());
        }
    }

    public List<ProdutoVariacao> listar() {
        List<ProdutoVariacao> lista = new ArrayList<>();

        String sql = "SELECT * FROM produto_variacao ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ProdutoVariacao pv = new ProdutoVariacao();

                pv.setId(rs.getInt("id"));
                pv.setProdutoId((Integer) rs.getObject("produto_id"));
                pv.setCorId((Integer) rs.getObject("cor_id"));
                pv.setTamanhoId((Integer) rs.getObject("tamanho_id"));
                pv.setCodigoBarras(rs.getString("codigo_barras"));
                pv.setCodigoInterno(rs.getString("codigo_interno"));

                lista.add(pv);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar produto_variacao: " + e.getMessage());
        }

        return lista;
    }
}
