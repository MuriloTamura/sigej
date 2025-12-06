package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.TipoOrdemServico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoOrdemServicoDAO {

    public void inserir(TipoOrdemServico t) {
        String sql = "INSERT INTO tipo_ordem_servico (descricao) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.executeUpdate();

            System.out.println("Tipo de ordem de serviço inserido!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir tipo_ordem_servico: " + e.getMessage());
        }
    }

    public List<TipoOrdemServico> listar() {
        List<TipoOrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_ordem_servico ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new TipoOrdemServico(
                        rs.getInt("id"),
                        rs.getString("descricao")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar tipo_ordem_servico: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(TipoOrdemServico t) {
        String sql = "UPDATE tipo_ordem_servico SET descricao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.setInt(2, t.getId());

            stmt.executeUpdate();
            System.out.println("Tipo de OS atualizado!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar tipo_ordem_servico: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tipo_ordem_servico WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Tipo de OS removido!");

        } catch (SQLException e) {

            if (e.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem ordens de serviço usando este tipo.");
            } else {
                System.out.println("Erro ao deletar tipo_ordem_servico: " + e.getMessage());
            }
        }
    }
}
