package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void inserir(Produto p) {
        String sql = """
            INSERT INTO produto (descricao, categoria_id, unidade_medida_id, marca_id)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getDescricao());
            stmt.setObject(2, p.getCategoriaId());
            stmt.setObject(3, p.getUnidadeMedidaId());
            stmt.setObject(4, p.getMarcaId());

            stmt.executeUpdate();
            System.out.println("Produto inserido com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao inserir produto: " + e.getMessage());
        }
    }

    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();

        String sql = "SELECT * FROM produto ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setCategoriaId((Integer) rs.getObject("categoria_id"));
                p.setUnidadeMedidaId((Integer) rs.getObject("unidade_medida_id"));
                p.setMarcaId((Integer) rs.getObject("marca_id"));

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }

        return lista;
    }
}
