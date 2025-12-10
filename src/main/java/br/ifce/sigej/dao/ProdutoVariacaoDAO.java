package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.ProdutoVariacao;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProdutoVariacaoDAO {

    public void inserir(ProdutoVariacao v) {
        String sql = "INSERT INTO produto_variacao (produto_id, cor_id, tamanho_id, codigo_barras, codigo_interno) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, v.getProdutoId());
            stmt.setObject(2, v.getCorId());
            stmt.setObject(3, v.getTamanhoId());
            stmt.setString(4, v.getCodigoBarras());
            stmt.setString(5, v.getCodigoInterno());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir variação do produto: " + e.getMessage(), e);
        }
    }

    public List<ProdutoVariacao> listar() {
        List<ProdutoVariacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto_variacao ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearProdutoVariacao(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar variações do produto: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<ProdutoVariacao> buscarPorId(int id) {
        String sql = "SELECT * FROM produto_variacao WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearProdutoVariacao(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar variação do produto: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(ProdutoVariacao v) {
        String sql = """
        UPDATE produto_variacao 
        SET produto_id=?, cor_id=?, tamanho_id=?, codigo_barras=?, codigo_interno=? 
        WHERE id=?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, v.getProdutoId());
            stmt.setObject(2, v.getCorId());
            stmt.setObject(3, v.getTamanhoId());
            stmt.setString(4, v.getCodigoBarras());
            stmt.setString(5, v.getCodigoInterno());
            stmt.setInt(6, v.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar variação do produto: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM produto_variacao WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: esta variação está sendo referenciada em outras tabelas.");
            }
            throw new RuntimeException("Erro ao deletar variação do produto: " + e.getMessage(), e);
        }
    }

    private ProdutoVariacao mapearProdutoVariacao(ResultSet rs) throws SQLException {
        ProdutoVariacao v = new ProdutoVariacao();
        v.setId(rs.getInt("id"));
        v.setProdutoId((Integer) rs.getObject("produto_id"));
        v.setCorId((Integer) rs.getObject("cor_id"));
        v.setTamanhoId((Integer) rs.getObject("tamanho_id"));
        v.setCodigoBarras(rs.getString("codigo_barras"));
        v.setCodigoInterno(rs.getString("codigo_interno"));
        return v;
    }
}