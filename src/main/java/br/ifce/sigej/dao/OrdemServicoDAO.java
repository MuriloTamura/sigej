package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.*;
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
        String sql = """
            SELECT 
                os.id, os.numero_sequencial, os.solicitante_id, os.area_campus_id, 
                os.tipo_os_id, os.equipe_id, os.lider_id, os.status_id, os.prioridade,
                os.data_abertura, os.data_prevista, os.descricao_problema,
                
                p.nome as solicitante_nome,
                
                ac.descricao as area_desc, ac.bloco as area_bloco,
                
                tos.descricao as tipo_desc,
                
                em.nome as equipe_nome,
                
                plider.nome as lider_nome,
                
                sos.descricao as status_desc
                
            FROM ordem_servico os
            LEFT JOIN pessoa p ON os.solicitante_id = p.id
            LEFT JOIN area_campus ac ON os.area_campus_id = ac.id
            LEFT JOIN tipo_ordem_servico tos ON os.tipo_os_id = tos.id
            LEFT JOIN equipe_manutencao em ON os.equipe_id = em.id
            LEFT JOIN funcionario f ON os.lider_id = f.id
            LEFT JOIN pessoa plider ON f.pessoa_id = plider.id
            LEFT JOIN status_ordem_servico sos ON os.status_id = sos.id
            
            ORDER BY os.id DESC
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
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

                // Mapear Solicitante
                String solicitanteNome = rs.getString("solicitante_nome");
                if (solicitanteNome != null) {
                    Pessoa solicitante = new Pessoa();
                    solicitante.setNome(solicitanteNome);
                    o.setSolicitante(solicitante);
                }

                // Mapear Área Campus
                String areaDesc = rs.getString("area_desc");
                if (areaDesc != null) {
                    AreaCampus area = new AreaCampus();
                    area.setDescricao(areaDesc);
                    area.setBloco(rs.getString("area_bloco"));
                    o.setAreaCampus(area);
                }

                // Mapear Tipo OS
                String tipoDesc = rs.getString("tipo_desc");
                if (tipoDesc != null) {
                    TipoOrdemServico tipo = new TipoOrdemServico();
                    tipo.setDescricao(tipoDesc);
                    o.setTipoOs(tipo);
                }

                // Mapear Equipe
                String equipeNome = rs.getString("equipe_nome");
                if (equipeNome != null) {
                    EquipeManutencao equipe = new EquipeManutencao();
                    equipe.setNome(equipeNome);
                    o.setEquipe(equipe);
                }

                // Mapear Líder
                String liderNome = rs.getString("lider_nome");
                if (liderNome != null) {
                    Funcionario lider = new Funcionario();
                    lider.setPessoaNome(liderNome);
                    o.setLider(lider);
                }

                // Mapear Status
                String statusDesc = rs.getString("status_desc");
                if (statusDesc != null) {
                    StatusOrdemServico status = new StatusOrdemServico();
                    status.setDescricao(statusDesc);
                    o.setStatus(status);
                }

                lista.add(o);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ordens de serviço: " + e.getMessage(), e);
        }

        return lista;
    }

    public Optional<OrdemServico> buscarPorId(int id) {
        String sql = """
            SELECT 
                os.id, os.numero_sequencial, os.solicitante_id, os.area_campus_id, 
                os.tipo_os_id, os.equipe_id, os.lider_id, os.status_id, os.prioridade,
                os.data_abertura, os.data_prevista, os.descricao_problema,
                
                p.nome as solicitante_nome,
                ac.descricao as area_desc, ac.bloco as area_bloco,
                tos.descricao as tipo_desc,
                em.nome as equipe_nome,
                plider.nome as lider_nome,
                sos.descricao as status_desc
                
            FROM ordem_servico os
            LEFT JOIN pessoa p ON os.solicitante_id = p.id
            LEFT JOIN area_campus ac ON os.area_campus_id = ac.id
            LEFT JOIN tipo_ordem_servico tos ON os.tipo_os_id = tos.id
            LEFT JOIN equipe_manutencao em ON os.equipe_id = em.id
            LEFT JOIN funcionario f ON os.lider_id = f.id
            LEFT JOIN pessoa plider ON f.pessoa_id = plider.id
            LEFT JOIN status_ordem_servico sos ON os.status_id = sos.id
            
            WHERE os.id = ?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
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

                // Mapear objetos relacionados (mesmo código do listar)
                String solicitanteNome = rs.getString("solicitante_nome");
                if (solicitanteNome != null) {
                    Pessoa solicitante = new Pessoa();
                    solicitante.setNome(solicitanteNome);
                    o.setSolicitante(solicitante);
                }

                String areaDesc = rs.getString("area_desc");
                if (areaDesc != null) {
                    AreaCampus area = new AreaCampus();
                    area.setDescricao(areaDesc);
                    area.setBloco(rs.getString("area_bloco"));
                    o.setAreaCampus(area);
                }

                String tipoDesc = rs.getString("tipo_desc");
                if (tipoDesc != null) {
                    TipoOrdemServico tipo = new TipoOrdemServico();
                    tipo.setDescricao(tipoDesc);
                    o.setTipoOs(tipo);
                }

                String equipeNome = rs.getString("equipe_nome");
                if (equipeNome != null) {
                    EquipeManutencao equipe = new EquipeManutencao();
                    equipe.setNome(equipeNome);
                    o.setEquipe(equipe);
                }

                String liderNome = rs.getString("lider_nome");
                if (liderNome != null) {
                    Funcionario lider = new Funcionario();
                    lider.setPessoaNome(liderNome);
                    o.setLider(lider);
                }

                String statusDesc = rs.getString("status_desc");
                if (statusDesc != null) {
                    StatusOrdemServico status = new StatusOrdemServico();
                    status.setDescricao(statusDesc);
                    o.setStatus(status);
                }

                return Optional.of(o);
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
}