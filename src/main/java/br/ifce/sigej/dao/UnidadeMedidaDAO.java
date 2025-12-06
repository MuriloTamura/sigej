package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.UnidadeMedida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadeMedidaDAO {

    public void inserir(UnidadeMedida u) {
        String sql = "INSERT INTO unidade_medida (sigla, descricao) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getSigla());
            stmt.setString(2, u.getDescricao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir unidade_medida: " + e.getMessage());
        }
    }

    public List<UnidadeMedida> listar() {
        List<UnidadeMedida> lista = new ArrayList<>();
        String sql = "SELECT * FROM unidade_medida ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new UnidadeMedida(
                        rs.getInt("id"),
                        rs.getString("sigla"),
                        rs.getString("descricao")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar unidade_medida: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(UnidadeMedida u) {
        String sql = "UPDATE unidade_medida SET sigla = ?, descricao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getSigla());
            stmt.setString(2, u.getDescricao());
            stmt.setInt(3, u.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar unidade_medida: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM unidade_medida WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem produtos vinculados a esta unidade de medida.");
            } else {
                System.out.println("Erro ao deletar unidade_medida: " + ex.getMessage());
            }
        }
    }
}
