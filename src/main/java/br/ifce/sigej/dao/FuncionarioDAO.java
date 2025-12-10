package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Funcionario;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FuncionarioDAO {

    public void inserir(Funcionario f) {
        String sql = """
                INSERT INTO funcionario 
                (pessoa_id, tipo_funcionario_id, setor_id, data_admissao, data_demissao)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, f.getPessoaId());
            stmt.setObject(2, f.getTipoFuncionarioId());
            stmt.setObject(3, f.getSetorId());
            stmt.setObject(4, f.getDataAdmissao());
            stmt.setObject(5, f.getDataDemissao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir funcionário: " + e.getMessage(), e);
        }
    }

    public List<Funcionario> listar() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = """
                SELECT f.*, 
                       p.nome as pessoa_nome,
                       tf.descricao as tipo_funcionario_descricao,
                       s.nome as setor_nome
                FROM funcionario f
                LEFT JOIN pessoa p ON f.pessoa_id = p.id
                LEFT JOIN tipo_funcionario tf ON f.tipo_funcionario_id = tf.id
                LEFT JOIN setor s ON f.setor_id = s.id
                ORDER BY f.id
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearFuncionarioComJoin(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<Funcionario> buscarPorId(int id) {
        String sql = "SELECT * FROM funcionario WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearFuncionario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(Funcionario f) {
        String sql = """
                UPDATE funcionario SET 
                pessoa_id=?, tipo_funcionario_id=?, setor_id=?, 
                data_admissao=?, data_demissao=? 
                WHERE id=?
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, f.getPessoaId());
            stmt.setObject(2, f.getTipoFuncionarioId());
            stmt.setObject(3, f.getSetorId());
            stmt.setObject(4, f.getDataAdmissao());
            stmt.setObject(5, f.getDataDemissao());
            stmt.setInt(6, f.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM funcionario WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: funcionário está sendo usado em equipe, movimentação ou ordem de serviço.");
            }
            throw new RuntimeException("Erro ao deletar funcionário: " + e.getMessage(), e);
        }
    }

    private Funcionario mapearFuncionario(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setId(rs.getInt("id"));
        f.setPessoaId((Integer) rs.getObject("pessoa_id"));
        f.setTipoFuncionarioId((Integer) rs.getObject("tipo_funcionario_id"));
        f.setSetorId((Integer) rs.getObject("setor_id"));
        f.setDataAdmissao(rs.getDate("data_admissao") != null ? rs.getDate("data_admissao").toLocalDate() : null);
        f.setDataDemissao(rs.getDate("data_demissao") != null ? rs.getDate("data_demissao").toLocalDate() : null);
        return f;
    }

    private Funcionario mapearFuncionarioComJoin(ResultSet rs) throws SQLException {
        Funcionario f = mapearFuncionario(rs);
        f.setPessoaNome(rs.getString("pessoa_nome"));
        f.setTipoFuncionarioDescricao(rs.getString("tipo_funcionario_descricao"));
        f.setSetorNome(rs.getString("setor_nome"));
        return f;
    }
}