package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.EquipeMembro;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EquipeMembroDAO {

    public void inserir(EquipeMembro m) {
        String sql = "INSERT INTO equipe_membro (equipe_id, funcionario_id, data_inicio, data_fim, funcao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, m.getEquipeId());
            stmt.setInt(2, m.getFuncionarioId());
            stmt.setDate(3, Date.valueOf(m.getDataInicio()));

            if (m.getDataFim() != null)
                stmt.setDate(4, Date.valueOf(m.getDataFim()));
            else
                stmt.setNull(4, Types.DATE);

            stmt.setString(5, m.getFuncao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir membro da equipe: " + e.getMessage(), e);
        }
    }

    public List<EquipeMembro> listar() {
        List<EquipeMembro> lista = new ArrayList<>();
        String sql = """
            SELECT em.*, 
                   e.nome AS equipe_nome,
                   p.nome AS funcionario_nome
            FROM equipe_membro em
            LEFT JOIN equipe_manutencao e ON em.equipe_id = e.id
            LEFT JOIN funcionario f ON em.funcionario_id = f.id
            LEFT JOIN pessoa p ON f.pessoa_id = p.id
            ORDER BY em.id
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearEquipeMembroComNomes(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar membros da equipe: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<EquipeMembro> buscarPorId(int id) {
        String sql = """
            SELECT em.*, 
                   e.nome AS equipe_nome,
                   p.nome AS funcionario_nome
            FROM equipe_membro em
            LEFT JOIN equipe_manutencao e ON em.equipe_id = e.id
            LEFT JOIN funcionario f ON em.funcionario_id = f.id
            LEFT JOIN pessoa p ON f.pessoa_id = p.id
            WHERE em.id = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearEquipeMembroComNomes(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar membro da equipe: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(EquipeMembro m) {
        String sql = """
        UPDATE equipe_membro 
        SET equipe_id=?, funcionario_id=?, data_inicio=?, data_fim=?, funcao=? 
        WHERE id=?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, m.getEquipeId());
            stmt.setInt(2, m.getFuncionarioId());
            stmt.setDate(3, Date.valueOf(m.getDataInicio()));

            if (m.getDataFim() != null)
                stmt.setDate(4, Date.valueOf(m.getDataFim()));
            else
                stmt.setNull(4, Types.DATE);

            stmt.setString(5, m.getFuncao());
            stmt.setInt(6, m.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar membro da equipe: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM equipe_membro WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: este membro está sendo referenciado em outras tabelas.");
            }
            throw new RuntimeException("Erro ao deletar membro da equipe: " + e.getMessage(), e);
        }
    }

    private EquipeMembro mapearEquipeMembroComNomes(ResultSet rs) throws SQLException {
        EquipeMembro m = new EquipeMembro();
        m.setId(rs.getInt("id"));
        m.setEquipeId(rs.getInt("equipe_id"));
        m.setFuncionarioId(rs.getInt("funcionario_id"));
        m.setDataInicio(rs.getDate("data_inicio").toLocalDate());

        Date dataFim = rs.getDate("data_fim");
        m.setDataFim(dataFim != null ? dataFim.toLocalDate() : null);

        m.setFuncao(rs.getString("funcao"));

        // Campos extras para exibição
        m.setEquipeNome(rs.getString("equipe_nome"));
        m.setFuncionarioNome(rs.getString("funcionario_nome"));

        return m;
    }
}