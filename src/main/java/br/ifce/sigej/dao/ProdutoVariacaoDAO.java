package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.ProdutoVariacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoVariacaoDAO {

    public void inserir(ProdutoVariacao v) {
        String sql = "INSERT INTO produto_variacao (produto_id, cor_id, tamanho_id, codigo_barras, codigo_interno) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, v.getProdutoId());
            stmt.setObject(2, v.getCorId());
            stmt.setObject(3, v.getTamanhoId());
            stmt.setString(4, v.getCodigoBarras());
            stmt.setString(5, v.getCodigoInterno());

            stmt.executeUpdate();
            System.out.println("Produto variação inserido!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir produto_variacao: " + e.getMessage());
        }
    }

    public List<ProdutoVariacao> listar() {
        List<ProdutoVariacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto_variacao ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new ProdutoVariacao(
                        rs.getInt("id"),
                        (Integer) rs.getObject("produto_id"),
                        (Integer) rs.getObject("cor_id"),
                        (Integer) rs.getObject("tamanho_id"),
                        rs.getString("codigo_barras"),
                        rs.getString("codigo_interno")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar produto_variacao: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(ProdutoVariacao v) {
        String sql = "UPDATE produto_variacao SET produto_id = ?, cor_id = ?, tamanho_id = ?, " +
                "codigo_barras = ?, codigo_interno = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, v.getProdutoId());
            stmt.setObject(2, v.getCorId());
            stmt.setObject(3, v.getTamanhoId());
            stmt.setString(4, v.getCodigoBarras());
            stmt.setString(5, v.getCodigoInterno());
            stmt.setInt(6, v.getId());

            stmt.executeUpdate();
            System.out.println("Produto variação atualizada!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto_variacao: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM produto_variacao WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Produto variação removida!");

        } catch (SQLException e) {

            if (e.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem registros (estoque, itens de OS, movimentos etc.) usando esta variação.");
            } else {
                System.out.println("Erro ao deletar produto_variacao: " + e.getMessage());
            }
        }
    }
}
