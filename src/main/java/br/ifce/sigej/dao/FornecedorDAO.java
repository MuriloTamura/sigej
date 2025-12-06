package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Fornecedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {

    public void inserir(Fornecedor f) {
        String sql = "INSERT INTO fornecedor (nome, cnpj) VALUES (?, ?)";

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
        String sql = "SELECT * FROM fornecedor ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Fornecedor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cnpj")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar fornecedores: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(Fornecedor f) {
        String sql = "UPDATE fornecedor SET nome = ?, cnpj = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCnpj());
            stmt.setInt(3, f.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar fornecedor: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM fornecedor WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {

            // se houver FK no futuro (ex. produto_fornecedor), já deixamos preparado
            if (ex.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem registros vinculados a este fornecedor.");
            } else {
                System.out.println("Erro ao deletar fornecedor: " + ex.getMessage());
            }
        }
    }
}
