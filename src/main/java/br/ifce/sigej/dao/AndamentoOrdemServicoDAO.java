package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.AndamentoOrdemServico;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
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

            stmt.setObject(1, a.getOsId());
            stmt.setObject(2, a.getStatusAnteriorId());
            stmt.setObject(3, a.getStatusNovoId());
            stmt.setObject(4, a.getFuncionarioId());
            stmt.setString(5, a.getDescricao());
            stmt.setObject(6, a.getInicioAtendimento());
            stmt.setObject(7, a.getFimAtendimento());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir andamento da OS: " + e.getMessage(), e);
        }
    }

    public List<AndamentoOrdemServico> listar() {
        List<AndamentoOrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM andamento_ordem_servico ORDER BY id DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearAndamentoOrdemServico(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar andamentos da OS: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<AndamentoOrdemServico> buscarPorId(int id) {
        String sql = "SELECT * FROM andamento_ordem_servico WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearAndamentoOrdemServico(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar andamento da OS: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(AndamentoOrdemServico a) {
        String sql = """
            UPDATE andamento_ordem_servico SET
            os_id=?, status_anterior_id=?, status_novo_id=?, funcionario_id=?, 
            descricao=?, inicio_atendimento=?, fim_atendimento=?
            WHERE id=?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, a.getOsId());
            stmt.setObject(2, a.getStatusAnteriorId());
            stmt.setObject(3, a.getStatusNovoId());
            stmt.setObject(4, a.getFuncionarioId());
            stmt.setString(5, a.getDescricao());
            stmt.setObject(6, a.getInicioAtendimento());
            stmt.setObject(7, a.getFimAtendimento());
            stmt.setInt(8, a.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar andamento da OS: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM andamento_ordem_servico WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: este andamento está sendo referenciado em outras tabelas.");
            }
            throw new RuntimeException("Erro ao deletar andamento da OS: " + e.getMessage(), e);
        }
    }

    private AndamentoOrdemServico mapearAndamentoOrdemServico(ResultSet rs) throws SQLException {
        AndamentoOrdemServico a = new AndamentoOrdemServico();
        a.setId(rs.getInt("id"));
        a.setOsId((Integer) rs.getObject("os_id"));

        Timestamp dataHora = rs.getTimestamp("data_hora");
        a.setDataHora(dataHora != null ? dataHora.toLocalDateTime() : null);

        a.setStatusAnteriorId((Integer) rs.getObject("status_anterior_id"));
        a.setStatusNovoId((Integer) rs.getObject("status_novo_id"));
        a.setFuncionarioId((Integer) rs.getObject("funcionario_id"));
        a.setDescricao(rs.getString("descricao"));

        Timestamp inicioAtendimento = rs.getTimestamp("inicio_atendimento");
        a.setInicioAtendimento(inicioAtendimento != null ? inicioAtendimento.toLocalDateTime() : null);

        Timestamp fimAtendimento = rs.getTimestamp("fim_atendimento");
        a.setFimAtendimento(fimAtendimento != null ? fimAtendimento.toLocalDateTime() : null);

        return a;
    }
}