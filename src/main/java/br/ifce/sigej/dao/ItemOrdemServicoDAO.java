package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.ItemOrdemServico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemOrdemServicoDAO {

    public void inserir(ItemOrdemServico i) {
        String sql = """
            INSERT INTO item_ordem_servico 
            (os_id, produto_variacao_id, quantidade_prevista, quantidade_usada)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, i.getOsId());
            stmt.setInt(2, i.getProdutoVariacaoId());
            stmt.setDouble(3, i.getQuantidadePrevista());
            stmt.setDouble(4, i.getQuantidadeUsada());

            stmt.executeUpdate();
            System.out.println("Item da OS inserido!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir item_ordem_servico: " + e.getMessage());
        }
    }

    public List<ItemOrdemServico> listar() {
        List<ItemOrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM item_ordem_servico ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ItemOrdemServico item = new ItemOrdemServico(
                        rs.getInt("id"),
                        rs.getInt("os_id"),
                        rs.getInt("produto_variacao_id"),
                        rs.getDouble("quantidade_prevista"),
                        rs.getDouble("quantidade_usada")
                );
                lista.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar item_ordem_servico: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(ItemOrdemServico i) {
        String sql = """
            UPDATE item_ordem_servico
            SET os_id = ?, produto_variacao_id = ?, quantidade_prevista = ?, quantidade_usada = ?
            WHERE id = ?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, i.getOsId());
            stmt.setInt(2, i.getProdutoVariacaoId());
            stmt.setDouble(3, i.getQuantidadePrevista());
            stmt.setDouble(4, i.getQuantidadeUsada());
            stmt.setInt(5, i.getId());

            stmt.executeUpdate();
            System.out.println("Item da OS atualizado!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar item_ordem_servico: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM item_ordem_servico WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Item da OS removido!");

        } catch (SQLException e) {

            if (e.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem referências associadas.");
            } else {
                System.out.println("Erro ao deletar item_ordem_servico: " + e.getMessage());
            }
        }
    }
}
