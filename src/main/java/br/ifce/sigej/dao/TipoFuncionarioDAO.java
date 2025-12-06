package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.TipoFuncionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoFuncionarioDAO {

    public void inserir(TipoFuncionario t) {
        String sql = "INSERT INTO tipo_funcionario (descricao) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir tipo_funcionario: " + e.getMessage());
        }
    }

    public List<TipoFuncionario> listar() {
        List<TipoFuncionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_funcionario ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new TipoFuncionario(
                        rs.getInt("id"),
                        rs.getString("descricao")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar tipo_funcionario: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(TipoFuncionario t) {
        String sql = "UPDATE tipo_funcionario SET descricao=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.setInt(2, t.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar tipo_funcionario: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tipo_funcionario WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                System.out.println("Não é possível excluir: tipo de funcionário está sendo usado em funcionário.");
            } else {
                System.out.println("Erro ao deletar tipo_funcionario: " + e.getMessage());
            }
        }
    }
}
