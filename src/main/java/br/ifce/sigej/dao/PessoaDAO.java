package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    public void inserir(Pessoa p) {
        String sql = "INSERT INTO pessoa (nome, cpf, matricula_siape, email, telefone, ativo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getMatriculaSiape());
            stmt.setString(4, p.getEmail());
            stmt.setString(5, p.getTelefone());
            stmt.setBoolean(6, p.isAtivo());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir pessoa: " + e.getMessage());
        }
    }

    public List<Pessoa> listar() {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT * FROM pessoa ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pessoa p = new Pessoa();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                p.setMatriculaSiape(rs.getString("matricula_siape"));
                p.setEmail(rs.getString("email"));
                p.setTelefone(rs.getString("telefone"));
                p.setAtivo(rs.getBoolean("ativo"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar pessoa: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(Pessoa p) {
        String sql = """
        UPDATE pessoa 
        SET nome=?, cpf=?, matricula_siape=?, email=?, telefone=?, ativo=? 
        WHERE id=?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getMatriculaSiape());
            stmt.setString(4, p.getEmail());
            stmt.setString(5, p.getTelefone());
            stmt.setBoolean(6, p.isAtivo());
            stmt.setInt(7, p.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pessoa: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM pessoa WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                System.out.println("Não é possível excluir: esta pessoa está sendo usada em funcionário, ordem de serviço ou outra tabela.");
            } else {
                System.out.println("Erro ao deletar pessoa: " + e.getMessage());
            }
        }
    }
}
