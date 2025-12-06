package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.EquipeManutencao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeManutencaoDAO {

    public void inserir(EquipeManutencao e) {
        String sql = "INSERT INTO equipe_manutencao (nome, turno) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getTurno());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir equipe_manutencao: " + ex.getMessage());
        }
    }

    public List<EquipeManutencao> listar() {
        List<EquipeManutencao> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipe_manutencao ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new EquipeManutencao(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("turno")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar equipes: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(EquipeManutencao e) {
        String sql = "UPDATE equipe_manutencao SET nome = ?, turno = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getTurno());
            stmt.setInt(3, e.getId());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar equipe_manutencao: " + ex.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM equipe_manutencao WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {

            if (ex.getMessage().contains("violates foreign key constraint")) {
                System.out.println("Não é possível excluir: a equipe está sendo usada em outra tabela.");
            } else {
                System.out.println("Erro ao deletar equipe_manutencao: " + ex.getMessage());
            }
        }
    }
}
