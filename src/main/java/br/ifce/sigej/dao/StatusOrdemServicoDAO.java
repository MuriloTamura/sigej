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

        } catch (Exception e) {
            System.out.println("Erro ao inserir status_os: " + e.getMessage());
        }
    }

    public List<StatusOrdemServico> listar() {
        List<StatusOrdemServico> lista = new ArrayList<>();

        String sql = "SELECT id, descricao FROM status_ordem_servico ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                StatusOrdemServico s = new StatusOrdemServico();
                s.setId(rs.getInt("id"));
                s.setDescricao(rs.getString("descricao"));
                lista.add(s);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar status_os: " + e.getMessage());
        }

        return lista;
    }
}
