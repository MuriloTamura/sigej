package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.MovimentoEstoque;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovimentoEstoqueDAO {

    public void inserir(MovimentoEstoque m) {
        String sql = """
            INSERT INTO movimento_estoque 
            (produto_variacao_id, local_estoque_id, tipo_movimento_id, quantidade, funcionario_id, ordem_servico_id, observacao)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, m.getProdutoVariacaoId());
            stmt.setInt(2, m.getLocalEstoqueId());
            stmt.setInt(3, m.getTipoMovimentoId());
            stmt.setDouble(4, m.getQuantidade());
            stmt.setObject(5, m.getFuncionarioId());
            stmt.setObject(6, m.getOrdemServicoId());
            stmt.setString(7, m.getObservacao());

            stmt.executeUpdate();
            System.out.println("Movimentação registrada!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir movimento_estoque: " + e.getMessage());
        }
    }

    public List<MovimentoEstoque> listar() {
        List<MovimentoEstoque> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimento_estoque ORDER BY id DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                MovimentoEstoque m = new MovimentoEstoque(
                        rs.getInt("id"),
                        rs.getInt("produto_variacao_id"),
                        rs.getInt("local_estoque_id"),
                        rs.getInt("tipo_movimento_id"),
                        rs.getDouble("quantidade"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        (Integer) rs.getObject("funcionario_id"),
                        (Integer) rs.getObject("ordem_servico_id"),
                        rs.getString("observacao")
                );

                lista.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar movimento_estoque: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(MovimentoEstoque m) {
        String sql = """
            UPDATE movimento_estoque
            SET produto_variacao_id = ?, local_estoque_id = ?, tipo_movimento_id = ?, 
                quantidade = ?, funcionario_id = ?, ordem_servico_id = ?, observacao = ?
            WHERE id = ?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, m.getProdutoVariacaoId());
            stmt.setInt(2, m.getLocalEstoqueId());
            stmt.setInt(3, m.getTipoMovimentoId());
            stmt.setDouble(4, m.getQuantidade());
            stmt.setObject(5, m.getFuncionarioId());
            stmt.setObject(6, m.getOrdemServicoId());
            stmt.setString(7, m.getObservacao());
            stmt.setInt(8, m.getId());

            stmt.executeUpdate();
            System.out.println("Movimentação atualizada!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar movimento_estoque: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM movimento_estoque WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Movimentação removida!");

        } catch (SQLException e) {
            System.out.println("Erro ao deletar movimento_estoque: " + e.getMessage());
        }
    }
}
