package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void inserir(Funcionario f) {

        String sql = """
                INSERT INTO funcionario
                (pessoa_id, tipo_funcionario_id, setor_id, data_admissao)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, f.getPessoaId());
            stmt.setInt(2, f.getTipoFuncionarioId());
            stmt.setInt(3, f.getSetorId());
            stmt.setDate(4, Date.valueOf(f.getDataAdmissao()));

            stmt.executeUpdate();
            System.out.println("Funcionário inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir funcionário: " + e.getMessage());
        }
    }


    public List<Funcionario> listar() {

        List<Funcionario> lista = new ArrayList<>();

        String sql = "SELECT * FROM funcionario ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Funcionario f = new Funcionario();

                f.setId(rs.getInt("id"));
                f.setPessoaId(rs.getInt("pessoa_id"));
                f.setTipoFuncionarioId(rs.getInt("tipo_funcionario_id"));
                f.setSetorId(rs.getInt("setor_id"));

                f.setDataAdmissao(rs.getDate("data_admissao").toLocalDate());

                Date demissao = rs.getDate("data_demissao");
                if (demissao != null) {
                    f.setDataDemissao(demissao.toLocalDate());
                }

                lista.add(f);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }

        return lista;
    }
}
