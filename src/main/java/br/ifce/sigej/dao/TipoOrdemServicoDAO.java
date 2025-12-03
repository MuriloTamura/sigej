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

        } catch (Exception e) {
            System.out.println("Erro ao inserir tipo_os: " + e.getMessage());
        }
    }

    public List<TipoOrdemServico> listar() {
        List<TipoOrdemServico> lista = new ArrayList<>();

        String sql = "SELECT id, descricao FROM tipo_ordem_servico ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TipoOrdemServico t = new TipoOrdemServico();
                t.setId(rs.getInt("id"));
                t.setDescricao(rs.getString("descricao"));
                lista.add(t);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar tipo_os: " + e.getMessage());
        }

        return lista;
    }
}
