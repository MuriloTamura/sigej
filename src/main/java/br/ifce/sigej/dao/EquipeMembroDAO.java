package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.EquipeMembro;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EquipeMembroDAO {

    public void inserir(EquipeMembro m) {
        String sql = "INSERT INTO equipe_membro (equipe_id, funcionario_id, data_inicio, data_fim, funcao) " +
                "VALUES (?, ?, ?, ?, ?)";

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
            System.out.println("Erro ao inserir equipe_membro: " + e.getMessage());
        }
    }

    public List<EquipeMembro> listar() {
        List<EquipeMembro> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipe_membro ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new EquipeMembro(
                        rs.getInt("id"),
                        rs.getInt("equipe_id"),
                        rs.getInt("funcionario_id"),
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_fim") != null ? rs.getDate("data_fim").toLocalDate() : null,
                        rs.getString("funcao")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar equipe_membro: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(EquipeMembro m) {
        String sql = "UPDATE equipe_membro SET equipe_id = ?, funcionario_id = ?, data_inicio = ?, data_fim = ?, funcao = ? " +
                "WHERE id = ?";

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
            System.out.println("Erro ao atualizar equipe_membro: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM equipe_membro WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: o membro está associado a outra entidade.");
            } else {
                System.out.println("Erro ao deletar equipe_membro: " + ex.getMessage());
            }
        }
    }
}
