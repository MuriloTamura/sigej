package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.CategoriaMaterial;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CategoriaMaterialDAO {

    public void inserir(CategoriaMaterial categoria) {
        String sql = "INSERT INTO categoria_material (nome) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir categoria de material: " + e.getMessage(), e);
        }
    }

    public List<CategoriaMaterial> listar() {
        List<CategoriaMaterial> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria_material ORDER BY nome";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CategoriaMaterial cat = new CategoriaMaterial();
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
                lista.add(cat);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar categorias de material: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<CategoriaMaterial> buscarPorId(int id) {
        String sql = "SELECT * FROM categoria_material WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                CategoriaMaterial cat = new CategoriaMaterial();
                cat.setId(rs.getInt("id"));
                cat.setNome(rs.getString("nome"));
                return Optional.of(cat);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar categoria de material: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(CategoriaMaterial c) {
        String sql = "UPDATE categoria_material SET nome=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar categoria de material: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM categoria_material WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("foreign key")) {
                throw new RuntimeException("Não é possível excluir: existem produtos vinculados a esta categoria.");
            }
            throw new RuntimeException("Erro ao deletar categoria de material: " + e.getMessage(), e);
        }
    }
}