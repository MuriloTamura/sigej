package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.OrdemServico;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
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
            stmt.setObject(2, o.getSolicitanteId());
            stmt.setObject(3, o.getAreaCampusId());
            stmt.setObject(4, o.getTipoOsId());
            stmt.setObject(5, o.getEquipeId());
            stmt.setObject(6, o.getLiderId());
            stmt.setObject(7, o.getStatusId());
            stmt.setObject(8, o.getPrioridade());
            stmt.setObject(9, o.getDataPrevista());
            stmt.setString(10, o.getDescricaoProblema());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir ordem de serviço: " + e.getMessage(), e);
        }
    }

    public List<OrdemServico> listar() {
        List<OrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordem_servico ORDER BY id DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearOrdemServico(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ordens de serviço: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<OrdemServico> buscarPorId(int id) {
        String sql = "SELECT * FROM ordem_servico WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearOrdemServico(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ordem de serviço: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void atualizar(OrdemServico o) {
        String sql = """
            UPDATE ordem_servico SET
            numero_sequencial=?, solicitante_id=?, area_campus_id=?, 
            tipo_os_id=?, equipe_id=?, lider_id=?, status_id=?, prioridade=?, 
            data_prevista=?, descricao_problema=?
            WHERE id=?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, o.getNumeroSequencial());
            stmt.setObject(2, o.getSolicitanteId());
            stmt.setObject(3, o.getAreaCampusId());
            stmt.setObject(4, o.getTipoOsId());
            stmt.setObject(5, o.getEquipeId());
            stmt.setObject(6, o.getLiderId());
            stmt.setObject(7, o.getStatusId());
            stmt.setObject(8, o.getPrioridade());
            stmt.setObject(9, o.getDataPrevista());
            stmt.setString(10, o.getDescricaoProblema());
            stmt.setInt(11, o.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar ordem de serviço: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM ordem_servico WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new RuntimeException("Não é possível excluir: existem itens ou andamentos vinculados a esta OS.");
            }
            throw new RuntimeException("Erro ao deletar ordem de serviço: " + e.getMessage(), e);
        }
    }

    private OrdemServico mapearOrdemServico(ResultSet rs) throws SQLException {
        OrdemServico o = new OrdemServico();
        o.setId(rs.getInt("id"));
        o.setNumeroSequencial(rs.getString("numero_sequencial"));
        o.setSolicitanteId((Integer) rs.getObject("solicitante_id"));
        o.setAreaCampusId((Integer) rs.getObject("area_campus_id"));
        o.setTipoOsId((Integer) rs.getObject("tipo_os_id"));
        o.setEquipeId((Integer) rs.getObject("equipe_id"));
        o.setLiderId((Integer) rs.getObject("lider_id"));
        o.setStatusId((Integer) rs.getObject("status_id"));
        o.setPrioridade((Integer) rs.getObject("prioridade"));

        Timestamp timestamp = rs.getTimestamp("data_abertura");
        o.setDataAbertura(timestamp != null ? timestamp.toLocalDateTime() : null);

        Date dataPrevista = rs.getDate("data_prevista");
        o.setDataPrevista(dataPrevista != null ? dataPrevista.toLocalDate() : null);

        o.setDescricaoProblema(rs.getString("descricao_problema"));
        return o;
    }
}