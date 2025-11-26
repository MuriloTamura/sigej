package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.TipoFuncionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoFuncionarioDAO {

    public void inserir(TipoFuncionario t) {
        String sql = "INSERT INTO tipo_funcionario (descricao) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getNome());
            stmt.executeUpdate();

            System.out.println("Tipo de funcionário inserido!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir tipo de funcionário: " + e.getMessage());
        }
    }


    public List<TipoFuncionario> listar() {
        List<TipoFuncionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_funcionario ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                TipoFuncionario t = new TipoFuncionario();
                t.setId(rs.getInt("id"));
                t.setNome(rs.getString("descricao"));
                lista.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar tipos: " + e.getMessage());
        }

        return lista;
    }
}
