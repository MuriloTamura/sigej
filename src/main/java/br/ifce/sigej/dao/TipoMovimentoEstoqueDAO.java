package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.TipoMovimentoEstoque;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TipoMovimentoEstoqueDAO {

    public void inserir(TipoMovimentoEstoque t) {
        String sql = "INSERT INTO tipo_movimento_estoque (descricao, sinal) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.setString(2, String.valueOf(t.getSinal()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir tipo de movimento de estoque: " + e.getMessage(), e);
        }
    }

    public List<TipoMovimentoEstoque> listar() {
        List<TipoMovimentoEstoque> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_movimento_estoque ORDER BY descricao";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearTipoMovimentoEstoque(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tipos de movimento de estoque: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<TipoMovimentoEstoque> buscarPorId(int id) {
        String sql = "SELECT * FROM tipo_movimento_estoque WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearTipoMovimentoEstoque(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tipo de movimento de estoque: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(TipoMovimentoEstoque t) {
        String sql = "UPDATE tipo_movimento_estoque SET descricao=?, sinal=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.setString(2, String.valueOf(t.getSinal()));
            stmt.setInt(3, t.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tipo de movimento de estoque: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tipo_movimento_estoque WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: existem movimentos de estoque associados a este tipo.");
            }
            throw new RuntimeException("Erro ao deletar tipo de movimento de estoque: " + e.getMessage(), e);
        }
    }

    private TipoMovimentoEstoque mapearTipoMovimentoEstoque(ResultSet rs) throws SQLException {
        TipoMovimentoEstoque t = new TipoMovimentoEstoque();
        t.setId(rs.getInt("id"));
        t.setDescricao(rs.getString("descricao"));
        t.setSinal(rs.getString("sinal").charAt(0));
        return t;
    }
}