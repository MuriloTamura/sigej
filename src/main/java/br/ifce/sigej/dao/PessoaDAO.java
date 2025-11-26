package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    // INSERT
    public void inserir(Pessoa p) {
        String sql = "INSERT INTO pessoa (nome, cpf, email, telefone, ativo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getTelefone());
            stmt.setBoolean(5, p.isAtivo());

            stmt.executeUpdate();
            System.out.println("Pessoa inserida com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao inserir pessoa: " + e.getMessage());
        }
    }

    // SELECT (listar todos)
    public List<Pessoa> listar() {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT * FROM pessoa ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Pessoa p = new Pessoa();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                p.setEmail(rs.getString("email"));
                p.setTelefone(rs.getString("telefone"));
                p.setAtivo(rs.getBoolean("ativo"));

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar pessoas: " + e.getMessage());
        }
        return lista;
    }
}
