package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.TipoMovimentoEstoque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoMovimentoEstoqueDAO {

    public void inserir(TipoMovimentoEstoque t) {
        String sql = """
            INSERT INTO tipo_movimento_estoque (descricao, sinal)
            VALUES (?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.setString(2, t.getSinal());

            stmt.executeUpdate();
            System.out.println("Tipo de movimento inserido!");

        } catch (Exception e) {
            System.out.println("Erro ao inserir tipo movimento: " + e.getMessage());
        }
    }

    public List<TipoMovimentoEstoque> listar() {
        List<TipoMovimentoEstoque> lista = new ArrayList<>();

        String sql = "SELECT * FROM tipo_movimento_estoque ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TipoMovimentoEstoque t = new TipoMovimentoEstoque();

                t.setId(rs.getInt("id"));
                t.setDescricao(rs.getString("descricao"));
                t.setSinal(rs.getString("sinal"));

                lista.add(t);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar tipos de movimento: " + e.getMessage());
        }

        return lista;
    }
}
