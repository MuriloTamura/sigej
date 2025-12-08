package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Tamanho;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TamanhoDAO {

    public void inserir(Tamanho t) {
        String sql = "INSERT INTO tamanho (descricao) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir tamanho: " + e.getMessage(), e);
        }
    }

    public List<Tamanho> listar() {
        List<Tamanho> lista = new ArrayList<>();
        String sql = "SELECT * FROM tamanho ORDER BY descricao";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Tamanho(
                        rs.getInt("id"),
                        rs.getString("descricao")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tamanhos: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<Tamanho> buscarPorId(int id) {
        String sql = "SELECT * FROM tamanho WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new Tamanho(
                        rs.getInt("id"),
                        rs.getString("descricao")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tamanho: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(Tamanho t) {
        String sql = "UPDATE tamanho SET descricao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getDescricao());
            stmt.setInt(2, t.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tamanho: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tamanho WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getMessage().contains("foreign key")) {
                throw new RuntimeException("Não é possível excluir: existem variações de produto usando este tamanho.");
            }
            throw new RuntimeException("Erro ao deletar tamanho: " + ex.getMessage(), ex);
        }
    }
}