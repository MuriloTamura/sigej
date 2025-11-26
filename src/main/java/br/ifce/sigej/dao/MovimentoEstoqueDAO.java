package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.MovimentoEstoque;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MovimentoEstoqueDAO {

    public void inserir(MovimentoEstoque m) {
        String sql = """
            INSERT INTO movimento_estoque (
                produto_variacao_id, local_estoque_id, tipo_movimento_id,
                quantidade, funcionario_id, ordem_servico_id, observacao
            ) VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, m.getProdutoVariacaoId());
            stmt.setObject(2, m.getLocalEstoqueId());
            stmt.setObject(3, m.getTipoMovimentoId());
            stmt.setBigDecimal(4, m.getQuantidade());
            stmt.setObject(5, m.getFuncionarioId());
            stmt.setObject(6, m.getOrdemServicoId());
            stmt.setString(7, m.getObservacao());

            stmt.executeUpdate();
            System.out.println("Movimentação inserida com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao inserir movimentação: " + e.getMessage());
        }
    }

    public List<MovimentoEstoque> listar() {
        List<MovimentoEstoque> lista = new ArrayList<>();

        String sql = "SELECT * FROM movimento_estoque ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MovimentoEstoque m = new MovimentoEstoque();

                m.setId(rs.getInt("id"));
                m.setProdutoVariacaoId((Integer) rs.getObject("produto_variacao_id"));
                m.setLocalEstoqueId((Integer) rs.getObject("local_estoque_id"));
                m.setTipoMovimentoId((Integer) rs.getObject("tipo_movimento_id"));
                m.setQuantidade(rs.getBigDecimal("quantidade"));
                m.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                m.setFuncionarioId((Integer) rs.getObject("funcionario_id"));
                m.setOrdemServicoId((Integer) rs.getObject("ordem_servico_id"));
                m.setObservacao(rs.getString("observacao"));

                lista.add(m);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar movimentações: " + e.getMessage());
        }

        return lista;
    }
}
