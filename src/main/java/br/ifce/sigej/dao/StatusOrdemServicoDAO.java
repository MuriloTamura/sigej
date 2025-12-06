package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.StatusOrdemServico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusOrdemServicoDAO {

    public void inserir(StatusOrdemServico s) {
        String sql = "INSERT INTO status_ordem_servico (descricao) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getDescricao());
            stmt.executeUpdate();

            System.out.println("Status de OS inserido!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir status_ordem_servico: " + e.getMessage());
        }
    }

    public List<StatusOrdemServico> listar() {
        List<StatusOrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM status_ordem_servico ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new StatusOrdemServico(
                        rs.getInt("id"),
                        rs.getString("descricao")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar status_ordem_servico: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(StatusOrdemServico s) {
        String sql = "UPDATE status_ordem_servico SET descricao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getDescricao());
            stmt.setInt(2, s.getId());

            stmt.executeUpdate();
            System.out.println("Status de OS atualizado!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar status_ordem_servico: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM status_ordem_servico WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Status de OS removido!");

        } catch (SQLException e) {

            if (e.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem ordens de serviço usando este status.");
            } else {
                System.out.println("Erro ao deletar status_ordem_servico: " + e.getMessage());
            }
        }
    }
}
