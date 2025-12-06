package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Setor;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SetorDAO {

    public void inserir(Setor s) {
        String sql = "INSERT INTO setor (nome, sigla) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getNome());
            stmt.setString(2, s.getSigla());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir setor: " + e.getMessage(), e);
        }
    }

    public List<Setor> listar() {
        List<Setor> lista = new ArrayList<>();
        String sql = "SELECT * FROM setor ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Setor s = new Setor();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setSigla(rs.getString("sigla"));
                lista.add(s);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar setores: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<Setor> buscarPorId(int id) {
        String sql = "SELECT * FROM setor WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Setor s = new Setor();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setSigla(rs.getString("sigla"));
                return Optional.of(s);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar setor: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(Setor s) {
        String sql = "UPDATE setor SET nome=?, sigla=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getNome());
            stmt.setString(2, s.getSigla());
            stmt.setInt(3, s.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar setor: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM setor WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: este setor está sendo usado em funcionários.");
            }
            throw new RuntimeException("Erro ao deletar setor: " + e.getMessage(), e);
        }
    }
}