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
                (pessoa_id, tipo_funcionario_id, setor_id, data_admissao, data_demissao)
                VALUES (?, ?, ?, ?, ?);
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, f.getPessoaId());
            stmt.setObject(2, f.getTipoFuncionarioId());
            stmt.setObject(3, f.getSetorId());
            stmt.setObject(4, f.getDataAdmissao());
            stmt.setObject(5, f.getDataDemissao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir funcionário: " + e.getMessage());
        }
    }

    public List<Funcionario> listar() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionario ORDER BY id;";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setPessoaId((Integer) rs.getObject("pessoa_id"));
                f.setTipoFuncionarioId((Integer) rs.getObject("tipo_funcionario_id"));
                f.setSetorId((Integer) rs.getObject("setor_id"));
                f.setDataAdmissao(rs.getDate("data_admissao") != null ? rs.getDate("data_admissao").toLocalDate() : null);
                f.setDataDemissao(rs.getDate("data_demissao") != null ? rs.getDate("data_demissao").toLocalDate() : null);

                lista.add(f);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(Funcionario f) {
        String sql = """
                UPDATE funcionario SET 
                pessoa_id=?, tipo_funcionario_id=?, setor_id=?, 
                data_admissao=?, data_demissao=? 
                WHERE id=?;
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, f.getPessoaId());
            stmt.setObject(2, f.getTipoFuncionarioId());
            stmt.setObject(3, f.getSetorId());
            stmt.setObject(4, f.getDataAdmissao());
            stmt.setObject(5, f.getDataDemissao());
            stmt.setInt(6, f.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar funcionário: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM funcionario WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {

            if (e.getMessage().contains("violates foreign key constraint")) {
                System.out.println("Não é possível excluir: funcionário está sendo usado em equipe, movimentação ou ordem de serviço.");
            } else {
                System.out.println("Erro ao deletar funcionário: " + e.getMessage());
            }
        }
    }
}
