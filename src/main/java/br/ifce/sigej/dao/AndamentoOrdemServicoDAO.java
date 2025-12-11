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
        String sql = """
            SELECT 
                a.id,
                a.os_id,
                a.data_hora,
                a.status_anterior_id,
                a.status_novo_id,
                a.funcionario_id,
                a.descricao,
                a.inicio_atendimento,
                a.fim_atendimento,
                os.numero_sequencial as os_numero,
                sa.descricao as status_anterior_desc,
                sn.descricao as status_novo_desc,
                p.nome as funcionario_nome
            FROM andamento_ordem_servico a
            LEFT JOIN ordem_servico os ON a.os_id = os.id
            LEFT JOIN status_ordem_servico sa ON a.status_anterior_id = sa.id
            LEFT JOIN status_ordem_servico sn ON a.status_novo_id = sn.id
            LEFT JOIN funcionario f ON a.funcionario_id = f.id
            LEFT JOIN pessoa p ON f.pessoa_id = p.id
            ORDER BY a.id DESC
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
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

                // Atributos adicionais para exibição
                a.setOsNumeroSequencial(rs.getString("os_numero"));
                a.setStatusAnteriorDescricao(rs.getString("status_anterior_desc"));
                a.setStatusNovoDescricao(rs.getString("status_novo_desc"));
                a.setFuncionarioNome(rs.getString("funcionario_nome"));

                lista.add(a);
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