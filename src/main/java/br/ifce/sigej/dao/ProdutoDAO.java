package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Produto;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
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

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir produto: " + e.getMessage(), e);
        }
    }

    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        String sql = """
            SELECT 
                p.id,
                p.descricao,
                p.categoria_id,
                p.unidade_medida_id,
                p.marca_id,
                c.nome as categoria_nome,
                u.sigla as unidade_sigla,
                m.nome as marca_nome
            FROM produto p
            LEFT JOIN categoria_material c ON p.categoria_id = c.id
            LEFT JOIN unidade_medida u ON p.unidade_medida_id = u.id
            LEFT JOIN marca m ON p.marca_id = m.id
            ORDER BY p.descricao
            """;

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

                // Atributos adicionais para exibição
                p.setCategoriaNome(rs.getString("categoria_nome"));
                p.setUnidadeSigla(rs.getString("unidade_sigla"));
                p.setMarcaNome(rs.getString("marca_nome"));

                lista.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<Produto> buscarPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setCategoriaId((Integer) rs.getObject("categoria_id"));
                p.setUnidadeMedidaId((Integer) rs.getObject("unidade_medida_id"));
                p.setMarcaId((Integer) rs.getObject("marca_id"));
                return Optional.of(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto: " + e.getMessage(), e);
        }

        return Optional.empty();
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

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint") || e.getMessage().contains("foreign key")) {
                throw new RuntimeException("Não é possível excluir: existem referências a este produto (variações, estoque, itens de OS etc.).");
            }
            throw new RuntimeException("Erro ao deletar produto: " + e.getMessage(), e);
        }
    }
}