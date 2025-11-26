package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.EquipeMembro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeMembroDAO {

    public void inserir(EquipeMembro m) {
        String sql = """
                INSERT INTO equipe_membro (equipe_id, funcionario_id, data_inicio, funcao)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, m.getEquipeId());
            stmt.setInt(2, m.getFuncionarioId());
            stmt.setDate(3, Date.valueOf(m.getDataInicio()));
            stmt.setString(4, m.getFuncao());

            stmt.executeUpdate();
            System.out.println("Membro adicionado à equipe!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir membro: " + e.getMessage());
        }
    }

    public List<EquipeMembro> listar() {

        List<EquipeMembro> lista = new ArrayList<>();

        String sql = "SELECT * FROM equipe_membro ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                EquipeMembro m = new EquipeMembro();
                m.setId(rs.getInt("id"));
                m.setEquipeId(rs.getInt("equipe_id"));
                m.setFuncionarioId(rs.getInt("funcionario_id"));
                m.setDataInicio(rs.getDate("data_inicio").toLocalDate());

                Date fim = rs.getDate("data_fim");
                if (fim != null) m.setDataFim(fim.toLocalDate());

                m.setFuncao(rs.getString("funcao"));

                lista.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar membros: " + e.getMessage());
        }

        return lista;
    }
}
