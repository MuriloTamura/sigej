package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Fornecedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {

    public void inserir(Fornecedor f) {
        String sql = "INSERT INTO fornecedor (nome, cnpj) VALUES (?, ?);";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCnpj());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir fornecedor: " + e.getMessage());
        }
    }

    public List<Fornecedor> listar() {
        List<Fornecedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM fornecedor ORDER BY id;";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Fornecedor f = new Fornecedor();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setCnpj(rs.getString("cnpj"));
                lista.add(f);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar fornecedor: " + e.getMessage());
        }

        return lista;
    }
}
