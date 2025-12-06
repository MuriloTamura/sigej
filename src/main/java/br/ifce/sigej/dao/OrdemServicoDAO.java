package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.OrdemServico;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoDAO {

    public void inserir(OrdemServico o) {
        String sql = """
            INSERT INTO ordem_servico 
            (numero_sequencial, solicitante_id, area_campus_id, tipo_os_id, equipe_id, 
             lider_id, status_id, prioridade, data_prevista, descricao_problema)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, o.getNumeroSequencial());
            stmt.setInt(2, o.getSolicitanteId());
            stmt.setInt(3, o.getAreaCampusId());
            stmt.setInt(4, o.getTipoOsId());
            stmt.setInt(5, o.getEquipeId());
            stmt.setInt(6, o.getLiderId());
            stmt.setInt(7, o.getStatusId());
            stmt.setInt(8, o.getPrioridade());
            stmt.setObject(9, o.getDataPrevista());
            stmt.setString(10, o.getDescricaoProblema());

            stmt.executeUpdate();
            System.out.println("Ordem de serviço criada!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir ordem_servico: " + e.getMessage());
        }
    }

    public List<OrdemServico> listar() {
        List<OrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordem_servico ORDER BY id DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                OrdemServico o = new OrdemServico(
                        rs.getInt("id"),
                        rs.getString("numero_sequencial"),
                        rs.getInt("solicitante_id"),
                        rs.getInt("area_campus_id"),
                        rs.getInt("tipo_os_id"),
                        rs.getInt("equipe_id"),
                        rs.getInt("lider_id"),
                        rs.getInt("status_id"),
                        rs.getInt("prioridade"),
                        rs.getTimestamp("data_abertura").toLocalDateTime(),
                        rs.getDate("data_prevista") != null ? rs.getDate("data_prevista").toLocalDate() : null,
                        rs.getString("descricao_problema")
                );

                lista.add(o);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar ordem_servico: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(OrdemServico o) {
        String sql = """
            UPDATE ordem_servico SET
            numero_sequencial = ?, solicitante_id = ?, area_campus_id = ?, 
            tipo_os_id = ?, equipe_id = ?, lider_id = ?, status_id = ?, prioridade = ?, 
            data_prevista = ?, descricao_problema = ?
            WHERE id = ?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, o.getNumeroSequencial());
            stmt.setInt(2, o.getSolicitanteId());
            stmt.setInt(3, o.getAreaCampusId());
            stmt.setInt(4, o.getTipoOsId());
            stmt.setInt(5, o.getEquipeId());
            stmt.setInt(6, o.getLiderId());
            stmt.setInt(7, o.getStatusId());
            stmt.setInt(8, o.getPrioridade());
            stmt.setObject(9, o.getDataPrevista());
            stmt.setString(10, o.getDescricaoProblema());
            stmt.setInt(11, o.getId());

            stmt.executeUpdate();
            System.out.println("Ordem de serviço atualizada!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar ordem_servico: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM ordem_servico WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Ordem de serviço removida!");

        } catch (SQLException e) {

            if (e.getMessage().contains("foreign key")) {
                System.out.println("Não é possível excluir: existem itens ou andamentos vinculados a esta OS.");
            } else {
                System.out.println("Erro ao deletar ordem_servico: " + e.getMessage());
            }
        }
    }
}
