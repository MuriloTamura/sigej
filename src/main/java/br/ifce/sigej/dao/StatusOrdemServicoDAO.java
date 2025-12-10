package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.StatusOrdemServico;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StatusOrdemServicoDAO {

    public void inserir(StatusOrdemServico s) {
        String sql = "INSERT INTO status_ordem_servico (descricao) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getDescricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir status de ordem de serviço: " + e.getMessage(), e);
        }
    }

    public List<StatusOrdemServico> listar() {
        List<StatusOrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM status_ordem_servico ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearStatusOrdemServico(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar status de ordem de serviço: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<StatusOrdemServico> buscarPorId(int id) {
        String sql = "SELECT * FROM status_ordem_servico WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearStatusOrdemServico(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar status de ordem de serviço: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(StatusOrdemServico s) {
        String sql = "UPDATE status_ordem_servico SET descricao=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getDescricao());
            stmt.setInt(2, s.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status de ordem de serviço: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM status_ordem_servico WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: existem ordens de serviço usando este status.");
            }
            throw new RuntimeException("Erro ao deletar status de ordem de serviço: " + e.getMessage(), e);
        }
    }

    private StatusOrdemServico mapearStatusOrdemServico(ResultSet rs) throws SQLException {
        StatusOrdemServico s = new StatusOrdemServico();
        s.setId(rs.getInt("id"));
        s.setDescricao(rs.getString("descricao"));
        return s;
    }
}