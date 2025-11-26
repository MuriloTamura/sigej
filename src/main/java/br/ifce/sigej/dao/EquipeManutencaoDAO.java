package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.EquipeManutencao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeManutencaoDAO {

    public void inserir(EquipeManutencao e) {
        String sql = "INSERT INTO equipe_manutencao (nome, turno) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getTurno());
            stmt.executeUpdate();

            System.out.println("Equipe criada com sucesso!");

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir equipe: " + ex.getMessage());
        }
    }

    public List<EquipeManutencao> listar() {

        List<EquipeManutencao> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipe_manutencao ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                EquipeManutencao e = new EquipeManutencao();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setTurno(rs.getString("turno"));
                lista.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao listar equipes: " + ex.getMessage());
        }

        return lista;
    }
}
