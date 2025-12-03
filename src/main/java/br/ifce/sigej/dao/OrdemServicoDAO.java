package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.OrdemServico;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoDAO {

    public void inserir(OrdemServico os) {
        String sql = """
                INSERT INTO ordem_servico 
                (numero_sequencial, solicitante_id, area_campus_id, tipo_os_id, equipe_id, 
                 lider_id, status_id, prioridade, data_prevista, descricao_problema)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, os.getNumeroSequencial());
            stmt.setObject(2, os.getSolicitanteId());
            stmt.setObject(3, os.getAreaCampusId());
            stmt.setObject(4, os.getTipoOsId());
            stmt.setObject(5, os.getEquipeId());
            stmt.setObject(6, os.getLiderId());
            stmt.setObject(7, os.getStatusId());
            stmt.setObject(8, os.getPrioridade());
            stmt.setObject(9, os.getDataPrevista());
            stmt.setString(10, os.getDescricaoProblema());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao inserir OS: " + e.getMessage());
        }
    }

    public List<OrdemServico> listar() {
        List<OrdemServico> lista = new ArrayList<>();

        String sql = "SELECT * FROM ordem_servico ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                OrdemServico os = new OrdemServico();
                os.setId(rs.getInt("id"));
                os.setNumeroSequencial(rs.getString("numero_sequencial"));
                os.setSolicitanteId((Integer) rs.getObject("solicitante_id"));
                os.setAreaCampusId((Integer) rs.getObject("area_campus_id"));
                os.setTipoOsId((Integer) rs.getObject("tipo_os_id"));
                os.setEquipeId((Integer) rs.getObject("equipe_id"));
                os.setLiderId((Integer) rs.getObject("lider_id"));
                os.setStatusId((Integer) rs.getObject("status_id"));
                os.setPrioridade((Integer) rs.getObject("prioridade"));
                os.setDataAbertura(rs.getTimestamp("data_abertura").toLocalDateTime());

                Date dp = rs.getDate("data_prevista");
                if (dp != null) os.setDataPrevista(dp.toLocalDate());

                os.setDescricaoProblema(rs.getString("descricao_problema"));

                lista.add(os);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar OS: " + e.getMessage());
        }

        return lista;
    }
}
