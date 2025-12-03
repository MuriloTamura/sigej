package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.UnidadeMedida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadeMedidaDAO {

    public void inserir(UnidadeMedida un) {
        String sql = "INSERT INTO unidade_medida (sigla, descricao) VALUES (?, ?);";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, un.getSigla());
            stmt.setString(2, un.getDescricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir unidade_medida: " + e.getMessage());
        }
    }

    public List<UnidadeMedida> listar() {
        List<UnidadeMedida> lista = new ArrayList<>();
        String sql = "SELECT * FROM unidade_medida ORDER BY id;";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UnidadeMedida un = new UnidadeMedida();
                un.setId(rs.getInt("id"));
                un.setSigla(rs.getString("sigla"));
                un.setDescricao(rs.getString("descricao"));
                lista.add(un);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar unidade_medida: " + e.getMessage());
        }

        return lista;
    }
}
