package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Estoque;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EstoqueDAO {

    public void inserir(Estoque e) {
        System.out.println("=== ENTRANDO NO MÉTODO INSERIR ===");
        System.out.println("ProdutoVariacaoId: " + e.getProdutoVariacaoId());
        System.out.println("LocalEstoqueId: " + e.getLocalEstoqueId());
        System.out.println("Quantidade: " + e.getQuantidade());
        System.out.println("PontoReposicao: " + e.getPontoReposicao());

        String sql = "INSERT INTO estoque (produto_variacao_id, local_estoque_id, quantidade, ponto_reposicao) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Conexão obtida: " + (conn != null));

            stmt.setInt(1, e.getProdutoVariacaoId());
            stmt.setInt(2, e.getLocalEstoqueId());
            stmt.setDouble(3, e.getQuantidade());
            stmt.setDouble(4, e.getPontoReposicao());

            System.out.println("SQL Preparado: " + sql);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Linhas afetadas: " + rowsAffected);
            System.out.println("=== INSERÇÃO CONCLUÍDA COM SUCESSO ===");

        } catch (SQLException ex) {
            System.err.println("=== ERRO NA INSERÇÃO ===");
            System.err.println("Mensagem: " + ex.getMessage());
            System.err.println("SQL State: " + ex.getSQLState());
            System.err.println("Error Code: " + ex.getErrorCode());
            ex.printStackTrace();
            throw new RuntimeException("Erro ao inserir item no estoque: " + ex.getMessage(), ex);
        }
    }

    public List<Estoque> listar() {
        List<Estoque> lista = new ArrayList<>();
        String sql = """
            SELECT e.*, 
                   pv.codigo_interno,
                   p.descricao as produto_descricao,
                   le.descricao as local_descricao
            FROM estoque e
            JOIN produto_variacao pv ON e.produto_variacao_id = pv.id
            JOIN produto p ON pv.produto_id = p.id
            JOIN local_estoque le ON e.local_estoque_id = le.id
            ORDER BY pv.codigo_interno, le.descricao
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Estoque e = new Estoque();
                e.setProdutoVariacaoId(rs.getInt("produto_variacao_id"));
                e.setLocalEstoqueId(rs.getInt("local_estoque_id"));
                e.setQuantidade(rs.getDouble("quantidade"));
                e.setPontoReposicao(rs.getDouble("ponto_reposicao"));
                e.setCodigoInterno(rs.getString("codigo_interno"));
                e.setProdutoDescricao(rs.getString("produto_descricao"));
                e.setLocalDescricao(rs.getString("local_descricao"));
                lista.add(e);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar estoque: " + ex.getMessage(), ex);
        }

        return lista;
    }

    public Optional<Estoque> buscarPorId(int produtoVariacaoId, int localEstoqueId) {
        String sql = """
            SELECT e.*, 
                   pv.codigo_interno,
                   p.descricao as produto_descricao,
                   le.descricao as local_descricao
            FROM estoque e
            JOIN produto_variacao pv ON e.produto_variacao_id = pv.id
            JOIN produto p ON pv.produto_id = p.id
            JOIN local_estoque le ON e.local_estoque_id = le.id
            WHERE e.produto_variacao_id = ? AND e.local_estoque_id = ?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produtoVariacaoId);
            stmt.setInt(2, localEstoqueId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Estoque e = new Estoque();
                e.setProdutoVariacaoId(rs.getInt("produto_variacao_id"));
                e.setLocalEstoqueId(rs.getInt("local_estoque_id"));
                e.setQuantidade(rs.getDouble("quantidade"));
                e.setPontoReposicao(rs.getDouble("ponto_reposicao"));
                e.setCodigoInterno(rs.getString("codigo_interno"));
                e.setProdutoDescricao(rs.getString("produto_descricao"));
                e.setLocalDescricao(rs.getString("local_descricao"));
                return Optional.of(e);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar item no estoque: " + ex.getMessage(), ex);
        }

        return Optional.empty();
    }

    public void atualizar(Estoque e) {
        System.out.println("=== ENTRANDO NO MÉTODO ATUALIZAR ===");
        System.out.println("ProdutoVariacaoId: " + e.getProdutoVariacaoId());
        System.out.println("LocalEstoqueId: " + e.getLocalEstoqueId());
        System.out.println("Quantidade: " + e.getQuantidade());
        System.out.println("PontoReposicao: " + e.getPontoReposicao());

        String sql = "UPDATE estoque SET quantidade = ?, ponto_reposicao = ? WHERE produto_variacao_id = ? AND local_estoque_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, e.getQuantidade());
            stmt.setDouble(2, e.getPontoReposicao());
            stmt.setInt(3, e.getProdutoVariacaoId());
            stmt.setInt(4, e.getLocalEstoqueId());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Linhas afetadas: " + rowsAffected);

            if (rowsAffected == 0) {
                System.err.println("ATENÇÃO: Nenhuma linha foi atualizada!");
            }
            System.out.println("=== ATUALIZAÇÃO CONCLUÍDA ===");

        } catch (SQLException ex) {
            System.err.println("=== ERRO NA ATUALIZAÇÃO ===");
            ex.printStackTrace();
            throw new RuntimeException("Erro ao atualizar estoque: " + ex.getMessage(), ex);
        }
    }

    public void deletar(int produtoVariacaoId, int localEstoqueId) {
        String sql = "DELETE FROM estoque WHERE produto_variacao_id = ? AND local_estoque_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produtoVariacaoId);
            stmt.setInt(2, localEstoqueId);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getMessage().contains("foreign key") || ex.getMessage().contains("violates")) {
                throw new RuntimeException("Não é possível excluir: existem movimentações associadas a esse estoque.");
            }
            throw new RuntimeException("Erro ao deletar estoque: " + ex.getMessage(), ex);
        }
    }
}