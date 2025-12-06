package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.AndamentoOrdemServico;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AndamentoOrdemServicoDAO {

    public void inserir(AndamentoOrdemServico a) {
        String sql = """
            INSERT INTO andamento_ordem_servico
            (os_id, status_anterior_id, status_novo_id, funcionario_id, descricao,
             inicio_atendimento, fim_atendimento)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getOsId());
            stmt.setObject(2, a.getStatusAnteriorId());
            stmt.setObject(3, a.getStatusNovoId());
            stmt.setObject(4, a.getFuncionarioId());
            stmt.setString(5, a.getDescricao());
            stmt.setObject(6, a.getInicioAtendimento());
            stmt.setObject(7, a.getFimAtendimento());

            stmt.executeUpdate();
            System.out.println("Andamento de OS registrado!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir andamento_ordem_servico: " + e.getMessage());
        }
    }

    public List<AndamentoOrdemServico> listar() {
        List<AndamentoOrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM andamento_ordem_servico ORDER BY id DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AndamentoOrdemServico a = new AndamentoOrdemServico(
                        rs.getInt("id"),
                        rs.getInt("os_id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        (Integer) rs.getObject("status_anterior_id"),
                        (Integer) rs.getObject("status_novo_id"),
                        (Integer) rs.getObject("funcionario_id"),
                        rs.getString("descricao"),
                        rs.getTimestamp("inicio_atendimento") != null ?
                                rs.getTimestamp("inicio_atendimento").toLocalDateTime() : null,
                        rs.getTimestamp("fim_atendimento") != null ?
                                rs.getTimestamp("fim_atendimento").toLocalDateTime() : null
                );

                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar andamento_ordem_servico: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(AndamentoOrdemServico a) {
        String sql = """
            UPDATE andamento_ordem_servico SET
            os_id = ?, status_anterior_id = ?, status_novo_id = ?, funcionario_id = ?, 
            descricao = ?, inicio_atendimento = ?, fim_atendimento = ?
            WHERE id = ?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, a.getOsId());
            stmt.setObject(2, a.getStatusAnteriorId());
            stmt.setObject(3, a.getStatusNovoId());
            stmt.setObject(4, a.getFuncionarioId());
            stmt.setString(5, a.getDescricao());
            stmt.setObject(6, a.getInicioAtendimento());
            stmt.setObject(7, a.getFimAtendimento());
            stmt.setInt(8, a.getId());

            stmt.executeUpdate();
            System.out.println("Andamento atualizado!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar andamento_ordem_servico: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM andamento_ordem_servico WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Andamento removido!");

        } catch (SQLException e) {
            System.out.println("Erro ao deletar andamento_ordem_servico: " + e.getMessage());
        }
    }
}
