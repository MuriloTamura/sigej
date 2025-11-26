package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.LocalEstoque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalEstoqueDAO {

    public void inserir(LocalEstoque l) {
        String sql = """
            INSERT INTO local_estoque (descricao, responsavel_id)
            VALUES (?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, l.getDescricao());
            stmt.setObject(2, l.getResponsavelId());

            stmt.executeUpdate();
            System.out.println("Local de estoque inserido!");
        } catch (Exception e) {
            System.out.println("Erro ao inserir local de estoque: " + e.getMessage());
        }
    }

    public List<LocalEstoque> listar() {
        List<LocalEstoque> lista = new ArrayList<>();

        String sql = "SELECT * FROM local_estoque ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LocalEstoque l = new LocalEstoque();

                l.setId(rs.getInt("id"));
                l.setDescricao(rs.getString("descricao"));
                l.setResponsavelId((Integer) rs.getObject("responsavel_id"));

                lista.add(l);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar local_estoque: " + e.getMessage());
        }

        return lista;
    }
}
