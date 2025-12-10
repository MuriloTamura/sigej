package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.EquipeManutencao;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EquipeManutencaoDAO {

    public void inserir(EquipeManutencao e) {
        String sql = "INSERT INTO equipe_manutencao (nome, turno) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getTurno());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir equipe de manutenção: " + ex.getMessage(), ex);
        }
    }

    public List<EquipeManutencao> listar() {
        List<EquipeManutencao> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipe_manutencao ORDER BY nome";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearEquipeManutencao(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar equipes de manutenção: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<EquipeManutencao> buscarPorId(int id) {
        String sql = "SELECT * FROM equipe_manutencao WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearEquipeManutencao(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar equipe de manutenção: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(EquipeManutencao e) {
        String sql = "UPDATE equipe_manutencao SET nome=?, turno=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getTurno());
            stmt.setInt(3, e.getId());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar equipe de manutenção: " + ex.getMessage(), ex);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM equipe_manutencao WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: esta equipe está sendo usada em ordens de serviço ou possui membros cadastrados.");
            }
            throw new RuntimeException("Erro ao deletar equipe de manutenção: " + ex.getMessage(), ex);
        }
    }

    private EquipeManutencao mapearEquipeManutencao(ResultSet rs) throws SQLException {
        EquipeManutencao e = new EquipeManutencao();
        e.setId(rs.getInt("id"));
        e.setNome(rs.getString("nome"));
        e.setTurno(rs.getString("turno"));
        return e;
    }
}