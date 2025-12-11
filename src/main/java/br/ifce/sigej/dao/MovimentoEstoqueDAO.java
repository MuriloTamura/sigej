package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.MovimentoEstoque;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MovimentoEstoqueDAO {

    public void inserir(MovimentoEstoque m) {
        String sql = """
            INSERT INTO movimento_estoque 
            (produto_variacao_id, local_estoque_id, tipo_movimento_id, quantidade, funcionario_id, ordem_servico_id, observacao)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, m.getProdutoVariacaoId());
            stmt.setObject(2, m.getLocalEstoqueId());
            stmt.setObject(3, m.getTipoMovimentoId());
            stmt.setDouble(4, m.getQuantidade());
            stmt.setObject(5, m.getFuncionarioId());
            stmt.setObject(6, m.getOrdemServicoId());
            stmt.setString(7, m.getObservacao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir movimento de estoque: " + e.getMessage(), e);
        }
    }

    public List<MovimentoEstoque> listar() {
        List<MovimentoEstoque> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimento_estoque ORDER BY id DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearMovimentoEstoque(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar movimentos de estoque: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<MovimentoEstoque> buscarPorId(int id) {
        String sql = "SELECT * FROM movimento_estoque WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearMovimentoEstoque(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar movimento de estoque: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(MovimentoEstoque m) {
        String sql = """
            UPDATE movimento_estoque
            SET produto_variacao_id=?, local_estoque_id=?, tipo_movimento_id=?, 
                quantidade=?, funcionario_id=?, ordem_servico_id=?, observacao=?
            WHERE id=?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, m.getProdutoVariacaoId());
            stmt.setObject(2, m.getLocalEstoqueId());
            stmt.setObject(3, m.getTipoMovimentoId());
            stmt.setDouble(4, m.getQuantidade());
            stmt.setObject(5, m.getFuncionarioId());
            stmt.setObject(6, m.getOrdemServicoId());
            stmt.setString(7, m.getObservacao());
            stmt.setInt(8, m.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar movimento de estoque: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM movimento_estoque WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: este movimento está sendo referenciado em outras tabelas.");
            }
            throw new RuntimeException("Erro ao deletar movimento de estoque: " + e.getMessage(), e);
        }
    }

    private MovimentoEstoque mapearMovimentoEstoque(ResultSet rs) throws SQLException {
        MovimentoEstoque m = new MovimentoEstoque();
        m.setId(rs.getInt("id"));
        m.setProdutoVariacaoId((Integer) rs.getObject("produto_variacao_id"));
        m.setLocalEstoqueId((Integer) rs.getObject("local_estoque_id"));
        m.setTipoMovimentoId((Integer) rs.getObject("tipo_movimento_id"));
        m.setQuantidade(rs.getDouble("quantidade"));

        Timestamp timestamp = rs.getTimestamp("data_hora");
        m.setDataHora(timestamp != null ? timestamp.toLocalDateTime() : null);

        m.setFuncionarioId((Integer) rs.getObject("funcionario_id"));
        m.setOrdemServicoId((Integer) rs.getObject("ordem_servico_id"));
        m.setObservacao(rs.getString("observacao"));
        return m;
    }
}