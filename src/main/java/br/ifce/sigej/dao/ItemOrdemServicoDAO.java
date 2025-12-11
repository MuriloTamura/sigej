package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.ItemOrdemServico;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ItemOrdemServicoDAO {

    public void inserir(ItemOrdemServico i) {
        String sql = """
            INSERT INTO item_ordem_servico 
            (os_id, produto_variacao_id, quantidade_prevista, quantidade_usada)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, i.getOsId());
            stmt.setObject(2, i.getProdutoVariacaoId());
            stmt.setDouble(3, i.getQuantidadePrevista());
            stmt.setDouble(4, i.getQuantidadeUsada());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir item da OS: " + e.getMessage(), e);
        }
    }

    public List<ItemOrdemServico> listar() {
        List<ItemOrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM item_ordem_servico ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearItemOrdemServico(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens da OS: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<ItemOrdemServico> buscarPorId(int id) {
        String sql = "SELECT * FROM item_ordem_servico WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearItemOrdemServico(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar item da OS: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(ItemOrdemServico i) {
        String sql = """
            UPDATE item_ordem_servico
            SET os_id=?, produto_variacao_id=?, quantidade_prevista=?, quantidade_usada=?
            WHERE id=?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, i.getOsId());
            stmt.setObject(2, i.getProdutoVariacaoId());
            stmt.setDouble(3, i.getQuantidadePrevista());
            stmt.setDouble(4, i.getQuantidadeUsada());
            stmt.setInt(5, i.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item da OS: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM item_ordem_servico WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: este item está sendo referenciado em outras tabelas.");
            }
            throw new RuntimeException("Erro ao deletar item da OS: " + e.getMessage(), e);
        }
    }

    private ItemOrdemServico mapearItemOrdemServico(ResultSet rs) throws SQLException {
        ItemOrdemServico i = new ItemOrdemServico();
        i.setId(rs.getInt("id"));
        i.setOsId((Integer) rs.getObject("os_id"));
        i.setProdutoVariacaoId((Integer) rs.getObject("produto_variacao_id"));
        i.setQuantidadePrevista(rs.getDouble("quantidade_prevista"));
        i.setQuantidadeUsada(rs.getDouble("quantidade_usada"));
        return i;
    }
}