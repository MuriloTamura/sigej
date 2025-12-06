package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void inserir(Produto p) {
        String sql = "INSERT INTO produto (descricao, categoria_id, unidade_medida_id, marca_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getDescricao());
            stmt.setObject(2, p.getCategoriaId());
            stmt.setObject(3, p.getUnidadeMedidaId());
            stmt.setObject(4, p.getMarcaId());

            stmt.executeUpdate();
            System.out.println("Produto inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir produto: " + e.getMessage());
        }
    }

    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setCategoriaId((Integer) rs.getObject("categoria_id"));
                p.setUnidadeMedidaId((Integer) rs.getObject("unidade_medida_id"));
                p.setMarcaId((Integer) rs.getObject("marca_id"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(Produto p) {
        String sql = "UPDATE produto SET descricao = ?, categoria_id = ?, unidade_medida_id = ?, marca_id = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getDescricao());
            stmt.setObject(2, p.getCategoriaId());
            stmt.setObject(3, p.getUnidadeMedidaId());
            stmt.setObject(4, p.getMarcaId());
            stmt.setInt(5, p.getId());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Produto não encontrado para atualizar.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Produto removido com sucesso!");
            } else {
                System.out.println("Produto não encontrado para exclusão.");
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint") || e.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem referências a este produto (variações, estoque, itens de OS etc.).");
            } else {
                System.out.println("Erro ao deletar produto: " + e.getMessage());
            }
        }
    }
}
