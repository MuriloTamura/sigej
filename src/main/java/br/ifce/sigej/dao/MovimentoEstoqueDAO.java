package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.*;
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
        String sql = """
            SELECT 
                me.id, me.produto_variacao_id, me.local_estoque_id, me.tipo_movimento_id,
                me.quantidade, me.data_hora, me.funcionario_id, me.ordem_servico_id, me.observacao,
                
                pv.id as pv_id, prod.descricao as produto_desc, c.nome as cor_nome, t.descricao as tamanho_desc,
                
                le.id as le_id, le.descricao as local_desc,
                
                tm.id as tm_id, tm.descricao as tipo_desc, tm.sinal as tipo_sinal,
                
                f.id as func_id, p.nome as pessoa_nome
                
            FROM movimento_estoque me
            LEFT JOIN produto_variacao pv ON me.produto_variacao_id = pv.id
            LEFT JOIN produto prod ON pv.produto_id = prod.id
            LEFT JOIN cor c ON pv.cor_id = c.id
            LEFT JOIN tamanho t ON pv.tamanho_id = t.id
            
            LEFT JOIN local_estoque le ON me.local_estoque_id = le.id
            
            LEFT JOIN tipo_movimento_estoque tm ON me.tipo_movimento_id = tm.id
            
            LEFT JOIN funcionario f ON me.funcionario_id = f.id
            LEFT JOIN pessoa p ON f.pessoa_id = p.id
            
            ORDER BY me.id DESC
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
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

                // Mapear ProdutoVariacao
                if (rs.getObject("pv_id") != null) {
                    ProdutoVariacao pv = new ProdutoVariacao();
                    pv.setId(rs.getInt("pv_id"));
                    pv.setProdutoDescricao(rs.getString("produto_desc"));
                    pv.setCorNome(rs.getString("cor_nome"));
                    pv.setTamanhoDescricao(rs.getString("tamanho_desc"));
                    m.setProdutoVariacao(pv);
                }

                // Mapear LocalEstoque
                if (rs.getObject("le_id") != null) {
                    LocalEstoque le = new LocalEstoque();
                    le.setId(rs.getInt("le_id"));
                    le.setDescricao(rs.getString("local_desc"));
                    m.setLocalEstoque(le);
                }

                // Mapear TipoMovimentoEstoque
                if (rs.getObject("tm_id") != null) {
                    TipoMovimentoEstoque tm = new TipoMovimentoEstoque();
                    tm.setId(rs.getInt("tm_id"));
                    tm.setDescricao(rs.getString("tipo_desc"));
                    String sinalStr = rs.getString("tipo_sinal");
                    tm.setSinal(sinalStr != null && !sinalStr.isEmpty() ? sinalStr.charAt(0) : null);
                    m.setTipoMovimento(tm);
                }

                // Mapear Funcionario
                if (rs.getObject("func_id") != null) {
                    Funcionario f = new Funcionario();
                    f.setId(rs.getInt("func_id"));
                    f.setPessoaNome(rs.getString("pessoa_nome"));
                    m.setFuncionario(f);
                }

                lista.add(m);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar movimentos de estoque: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<MovimentoEstoque> buscarPorId(int id) {
        String sql = """
            SELECT 
                me.id, me.produto_variacao_id, me.local_estoque_id, me.tipo_movimento_id,
                me.quantidade, me.data_hora, me.funcionario_id, me.ordem_servico_id, me.observacao,
                
                pv.id as pv_id, prod.descricao as produto_desc, c.nome as cor_nome, t.descricao as tamanho_desc,
                
                le.id as le_id, le.descricao as local_desc,
                
                tm.id as tm_id, tm.descricao as tipo_desc, tm.sinal as tipo_sinal,
                
                f.id as func_id, p.nome as pessoa_nome
                
            FROM movimento_estoque me
            LEFT JOIN produto_variacao pv ON me.produto_variacao_id = pv.id
            LEFT JOIN produto prod ON pv.produto_id = prod.id
            LEFT JOIN cor c ON pv.cor_id = c.id
            LEFT JOIN tamanho t ON pv.tamanho_id = t.id
            
            LEFT JOIN local_estoque le ON me.local_estoque_id = le.id
            
            LEFT JOIN tipo_movimento_estoque tm ON me.tipo_movimento_id = tm.id
            
            LEFT JOIN funcionario f ON me.funcionario_id = f.id
            LEFT JOIN pessoa p ON f.pessoa_id = p.id
            
            WHERE me.id = ?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
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

                // Mapear objetos relacionados (mesmo código do listar)
                if (rs.getObject("pv_id") != null) {
                    ProdutoVariacao pv = new ProdutoVariacao();
                    pv.setId(rs.getInt("pv_id"));
                    pv.setProdutoDescricao(rs.getString("produto_desc"));
                    pv.setCorNome(rs.getString("cor_nome"));
                    pv.setTamanhoDescricao(rs.getString("tamanho_desc"));
                    m.setProdutoVariacao(pv);
                }

                if (rs.getObject("le_id") != null) {
                    LocalEstoque le = new LocalEstoque();
                    le.setId(rs.getInt("le_id"));
                    le.setDescricao(rs.getString("local_desc"));
                    m.setLocalEstoque(le);
                }

                if (rs.getObject("tm_id") != null) {
                    TipoMovimentoEstoque tm = new TipoMovimentoEstoque();
                    tm.setId(rs.getInt("tm_id"));
                    tm.setDescricao(rs.getString("tipo_desc"));
                    String sinalStr = rs.getString("tipo_sinal");
                    tm.setSinal(sinalStr != null && !sinalStr.isEmpty() ? sinalStr.charAt(0) : null);
                    m.setTipoMovimento(tm);
                }

                if (rs.getObject("func_id") != null) {
                    Funcionario f = new Funcionario();
                    f.setId(rs.getInt("func_id"));
                    f.setPessoaNome(rs.getString("pessoa_nome"));
                    m.setFuncionario(f);
                }

                return Optional.of(m);
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
}