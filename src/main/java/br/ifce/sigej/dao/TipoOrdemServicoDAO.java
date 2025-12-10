package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.TipoOrdemServico;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TipoOrdemServicoDAO {

    public void inserir(TipoOrdemServico t) {
        String sql = "INSERT INTO tipo_ordem_servico (descricao) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir tipo de ordem de serviço: " + e.getMessage(), e);
        }
    }

    public List<TipoOrdemServico> listar() {
        List<TipoOrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_ordem_servico ORDER BY descricao";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearTipoOrdemServico(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tipos de ordem de serviço: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<TipoOrdemServico> buscarPorId(int id) {
        String sql = "SELECT * FROM tipo_ordem_servico WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearTipoOrdemServico(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tipo de ordem de serviço: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(TipoOrdemServico t) {
        String sql = "UPDATE tipo_ordem_servico SET descricao=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.setInt(2, t.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tipo de ordem de serviço: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tipo_ordem_servico WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: existem ordens de serviço usando este tipo.");
            }
            throw new RuntimeException("Erro ao deletar tipo de ordem de serviço: " + e.getMessage(), e);
        }
    }

    private TipoOrdemServico mapearTipoOrdemServico(ResultSet rs) throws SQLException {
        TipoOrdemServico t = new TipoOrdemServico();
        t.setId(rs.getInt("id"));
        t.setDescricao(rs.getString("descricao"));
        return t;
    }
}