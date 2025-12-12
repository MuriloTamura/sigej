package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RelatoriosDAO {

    /**
     * 1. Ordens de serviço em aberto por prioridade e área
     */
    public List<Map<String, Object>> ordensServicoAbertas() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = """
            SELECT 
                os.id, 
                os.numero_sequencial, 
                os.prioridade, 
                ac.descricao AS area, 
                tos.descricao AS tipo_servico, 
                p.nome AS solicitante, 
                os.data_abertura
            FROM ordem_servico os
            JOIN area_campus ac ON os.area_campus_id = ac.id
            JOIN tipo_ordem_servico tos ON os.tipo_os_id = tos.id
            JOIN status_ordem_servico sts ON os.status_id = sts.id
            JOIN pessoa p ON os.solicitante_id = p.id
            WHERE sts.descricao IN ('aberta', 'em_atendimento', 'aguardando_material')
            ORDER BY os.prioridade ASC, os.data_abertura ASC
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("id", rs.getInt("id"));
                registro.put("numero_sequencial", rs.getString("numero_sequencial"));
                registro.put("prioridade", rs.getInt("prioridade"));
                registro.put("area", rs.getString("area"));
                registro.put("tipo_servico", rs.getString("tipo_servico"));
                registro.put("solicitante", rs.getString("solicitante"));
                registro.put("data_abertura", rs.getTimestamp("data_abertura"));
                lista.add(registro);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar OS abertas: " + e.getMessage(), e);
        }

        return lista;
    }

    /**
     * 2. Materiais abaixo do ponto de reposição
     */
    public List<Map<String, Object>> materiaisAbaixoPontoReposicao() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = """
            SELECT 
                p.descricao, 
                pv.codigo_interno, 
                le.descricao AS local, 
                e.quantidade,
                e.ponto_reposicao
            FROM estoque e
            JOIN produto_variacao pv ON e.produto_variacao_id = pv.id
            JOIN produto p ON pv.produto_id = p.id
            JOIN local_estoque le ON e.local_estoque_id = le.id
            WHERE e.quantidade < e.ponto_reposicao
            ORDER BY (e.ponto_reposicao - e.quantidade) DESC
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("descricao", rs.getString("descricao"));
                registro.put("codigo_interno", rs.getString("codigo_interno"));
                registro.put("local", rs.getString("local"));
                registro.put("quantidade", rs.getDouble("quantidade"));
                registro.put("ponto_reposicao", rs.getDouble("ponto_reposicao"));
                lista.add(registro);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar materiais baixo estoque: " + e.getMessage(), e);
        }

        return lista;
    }

    /**
     * 3. Timeline de uma OS
     */
    public List<Map<String, Object>> timelineOrdemServico(int osId) {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = """
            SELECT 
                a.data_hora, 
                pes.nome AS funcionario, 
                sts_novo.descricao AS status_atual,
                a.descricao
            FROM andamento_ordem_servico a
            LEFT JOIN funcionario f ON a.funcionario_id = f.id
            LEFT JOIN pessoa pes ON f.pessoa_id = pes.id
            JOIN status_ordem_servico sts_novo ON a.status_novo_id = sts_novo.id
            WHERE a.os_id = ?
            ORDER BY a.data_hora
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, osId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("data_hora", rs.getTimestamp("data_hora"));
                registro.put("funcionario", rs.getString("funcionario"));
                registro.put("status_atual", rs.getString("status_atual"));
                registro.put("descricao", rs.getString("descricao"));
                lista.add(registro);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar timeline da OS: " + e.getMessage(), e);
        }

        return lista;
    }

    /**
     * 4. Consumo por equipe em um período
     */
    public List<Map<String, Object>> consumoPorEquipe(String dataInicio, String dataFim) {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = """
            SELECT 
                eq.nome AS equipe, 
                p.descricao, 
                SUM(me.quantidade) AS total
            FROM movimento_estoque me
            JOIN produto_variacao pv ON me.produto_variacao_id = pv.id
            JOIN produto p ON pv.produto_id = p.id
            JOIN ordem_servico os ON me.ordem_servico_id = os.id
            JOIN equipe_manutencao eq ON os.equipe_id = eq.id
            WHERE me.data_hora BETWEEN ? AND ?
            GROUP BY eq.nome, p.descricao
            ORDER BY eq.nome, total DESC
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(dataInicio + " 00:00:00"));
            stmt.setTimestamp(2, Timestamp.valueOf(dataFim + " 23:59:59"));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("equipe", rs.getString("equipe"));
                registro.put("descricao", rs.getString("descricao"));
                registro.put("total", rs.getDouble("total"));
                lista.add(registro);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar consumo por equipe: " + e.getMessage(), e);
        }

        return lista;
    }

    /**
     * 5. OS concluídas por tipo no ano
     */
    public List<Map<String, Object>> osConcluidasPorTipo(int ano) {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = """
            SELECT 
                tos.descricao, 
                COUNT(*) AS total
            FROM ordem_servico os
            JOIN tipo_ordem_servico tos ON os.tipo_os_id = tos.id
            JOIN status_ordem_servico sts ON os.status_id = sts.id
            WHERE sts.descricao = 'concluída' 
            AND EXTRACT(YEAR FROM os.data_abertura) = ?
            GROUP BY tos.descricao
            ORDER BY total DESC
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ano);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("descricao", rs.getString("descricao"));
                registro.put("total", rs.getInt("total"));
                lista.add(registro);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar OS concluídas: " + e.getMessage(), e);
        }

        return lista;
    }
}