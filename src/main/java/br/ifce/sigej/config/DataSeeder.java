package br.ifce.sigej.config;

import br.ifce.sigej.database.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("dev")
public class DataSeeder {

    // Mapas para armazenar IDs gerados pelo banco
    private final Map<String, Integer> ids = new HashMap<>();

    @Bean
    public CommandLineRunner seedDatabase() {
        return args -> {
            if (!isDatabaseEmpty()) {
                System.out.println("Banco já possui dados. Seed ignorado.");
                return;
            }

            System.out.println("Populando banco com dados mockados...");

            try (Connection conn = ConnectionFactory.getConnection()) {
                conn.setAutoCommit(false);

                try {
                    seedAll(conn);
                    conn.commit();
                    System.out.println("✓ Banco populado com sucesso!");

                } catch (SQLException e) {
                    conn.rollback();
                    throw new RuntimeException("Erro ao popular banco: " + e.getMessage(), e);
                }
            }
        };
    }

    private boolean isDatabaseEmpty() {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM pessoa");
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
            return true;

        } catch (SQLException e) {
            return true;
        }
    }

    private void seedAll(Connection conn) throws SQLException {
        // 1. Pessoas
        ids.put("joao", insert(conn, "pessoa", "nome, cpf, matricula_siape, email, telefone, ativo",
                "'João Silva', '12345678901', 'SIAPE001', 'joao@ifce.br', '85999999999', true"));
        ids.put("maria", insert(conn, "pessoa", "nome, cpf, matricula_siape, email, telefone, ativo",
                "'Maria Santos', '23456789012', 'SIAPE002', 'maria@ifce.br', '85988888888', true"));
        ids.put("pedro", insert(conn, "pessoa", "nome, cpf, matricula_siape, email, telefone, ativo",
                "'Pedro Costa', '34567890123', 'SIAPE003', 'pedro@ifce.br', '85977777777', true"));
        ids.put("ana", insert(conn, "pessoa", "nome, cpf, matricula_siape, email, telefone, ativo",
                "'Ana Oliveira', '45678901234', 'SIAPE004', 'ana@ifce.br', '85966666666', true"));
        ids.put("carlos", insert(conn, "pessoa", "nome, cpf, email, telefone, ativo",
                "'Carlos Mendes', '56789012345', 'carlos@ifce.br', '85955555555', true"));
        System.out.println("  ✓ Pessoas inseridas");

        // 2. Setores
        ids.put("setor_manut", insert(conn, "setor", "nome, sigla", "'Manutenção Predial', 'MANUT'"));
        ids.put("setor_almox", insert(conn, "setor", "nome, sigla", "'Almoxarifado', 'ALMOX'"));
        ids.put("setor_infra", insert(conn, "setor", "nome, sigla", "'Infraestrutura', 'INFRA'"));
        System.out.println("  ✓ Setores inseridos");

        // 3. Tipos de funcionário
        ids.put("tipo_tecnico", insert(conn, "tipo_funcionario", "descricao", "'Técnico'"));
        ids.put("tipo_coord", insert(conn, "tipo_funcionario", "descricao", "'Coordenador'"));
        ids.put("tipo_assist", insert(conn, "tipo_funcionario", "descricao", "'Assistente'"));
        System.out.println("  ✓ Tipos de funcionário inseridos");

        // 4. Funcionários
        ids.put("func_maria", insert(conn, "funcionario", "pessoa_id, tipo_funcionario_id, setor_id, data_admissao",
                ids.get("maria") + ", " + ids.get("tipo_tecnico") + ", " + ids.get("setor_manut") + ", '2020-01-15'"));
        ids.put("func_pedro", insert(conn, "funcionario", "pessoa_id, tipo_funcionario_id, setor_id, data_admissao",
                ids.get("pedro") + ", " + ids.get("tipo_coord") + ", " + ids.get("setor_manut") + ", '2019-03-10'"));
        ids.put("func_ana", insert(conn, "funcionario", "pessoa_id, tipo_funcionario_id, setor_id, data_admissao",
                ids.get("ana") + ", " + ids.get("tipo_tecnico") + ", " + ids.get("setor_manut") + ", '2021-06-20'"));
        System.out.println("  ✓ Funcionários inseridos");

        // 5. Tipos de área
        ids.put("tipo_sala", insert(conn, "tipo_area_campus", "descricao", "'Sala de Aula'"));
        ids.put("tipo_lab", insert(conn, "tipo_area_campus", "descricao", "'Laboratório'"));
        ids.put("tipo_adm", insert(conn, "tipo_area_campus", "descricao", "'Área Administrativa'"));
        ids.put("tipo_comum", insert(conn, "tipo_area_campus", "descricao", "'Área Comum'"));
        ids.put("tipo_externa", insert(conn, "tipo_area_campus", "descricao", "'Área Externa'"));
        System.out.println("  ✓ Tipos de área do campus inseridos");

        // 6. Áreas
        ids.put("area_a", insert(conn, "area_campus", "tipo_area_id, descricao, bloco",
                ids.get("tipo_adm") + ", 'Bloco A - Administração', 'A'"));
        ids.put("area_b", insert(conn, "area_campus", "tipo_area_id, descricao, bloco",
                ids.get("tipo_lab") + ", 'Bloco B - Laboratórios', 'B'"));
        ids.put("area_c", insert(conn, "area_campus", "tipo_area_id, descricao, bloco",
                ids.get("tipo_sala") + ", 'Bloco C - Salas de Aula', 'C'"));
        ids.put("area_bib", insert(conn, "area_campus", "tipo_area_id, descricao, bloco",
                ids.get("tipo_comum") + ", 'Biblioteca', 'D'"));
        ids.put("area_gin", insert(conn, "area_campus", "tipo_area_id, descricao, bloco",
                ids.get("tipo_externa") + ", 'Ginásio', 'E'"));
        System.out.println("  ✓ Áreas do campus inseridas");

        // 7. Equipes
        ids.put("eq_elet", insert(conn, "equipe_manutencao", "nome, turno", "'Equipe Elétrica', 'Manhã'"));
        ids.put("eq_hidra", insert(conn, "equipe_manutencao", "nome, turno", "'Equipe Hidráulica', 'Tarde'"));
        ids.put("eq_civil", insert(conn, "equipe_manutencao", "nome, turno", "'Equipe Civil', 'Integral'"));
        System.out.println("  ✓ Equipes inseridas");

        // 8. Tipos de OS
        ids.put("os_elet", insert(conn, "tipo_ordem_servico", "descricao", "'Elétrica'"));
        ids.put("os_hidra", insert(conn, "tipo_ordem_servico", "descricao", "'Hidráulica'"));
        ids.put("os_civil", insert(conn, "tipo_ordem_servico", "descricao", "'Civil'"));
        ids.put("os_marc", insert(conn, "tipo_ordem_servico", "descricao", "'Marcenaria'"));
        insert(conn, "tipo_ordem_servico", "descricao", "'Jardinagem'");
        insert(conn, "tipo_ordem_servico", "descricao", "'Climatização'");
        System.out.println("  ✓ Tipos de OS inseridos");

        // 9. Status
        ids.put("st_aberta", insert(conn, "status_ordem_servico", "descricao", "'aberta'"));
        ids.put("st_atend", insert(conn, "status_ordem_servico", "descricao", "'em_atendimento'"));
        ids.put("st_aguard", insert(conn, "status_ordem_servico", "descricao", "'aguardando_material'"));
        ids.put("st_concl", insert(conn, "status_ordem_servico", "descricao", "'concluída'"));
        insert(conn, "status_ordem_servico", "descricao", "'cancelada'");
        System.out.println("  ✓ Status de OS inseridos");

        // 10. Ordens de Serviço
        ids.put("os1", insert(conn, "ordem_servico",
                "numero_sequencial, solicitante_id, area_campus_id, tipo_os_id, equipe_id, status_id, prioridade, data_abertura, descricao_problema",
                "'OS-2025-001', " + ids.get("joao") + ", " + ids.get("area_a") + ", " + ids.get("os_elet") + ", " +
                        ids.get("eq_elet") + ", " + ids.get("st_aberta") + ", 1, '2025-01-10 08:30:00', 'Lâmpadas queimadas na sala 101'"));

        ids.put("os2", insert(conn, "ordem_servico",
                "numero_sequencial, solicitante_id, area_campus_id, tipo_os_id, equipe_id, status_id, prioridade, data_abertura, descricao_problema",
                "'OS-2025-002', " + ids.get("joao") + ", " + ids.get("area_b") + ", " + ids.get("os_hidra") + ", " +
                        ids.get("eq_hidra") + ", " + ids.get("st_atend") + ", 2, '2025-02-15 10:00:00', 'Vazamento no banheiro'"));

        ids.put("os3", insert(conn, "ordem_servico",
                "numero_sequencial, solicitante_id, area_campus_id, tipo_os_id, equipe_id, status_id, prioridade, data_abertura, descricao_problema",
                "'OS-2025-003', " + ids.get("carlos") + ", " + ids.get("area_c") + ", " + ids.get("os_civil") + ", " +
                        ids.get("eq_civil") + ", " + ids.get("st_aguard") + ", 1, '2025-03-20 14:30:00', 'Infiltração na parede'"));

        ids.put("os4", insert(conn, "ordem_servico",
                "numero_sequencial, solicitante_id, area_campus_id, tipo_os_id, equipe_id, status_id, prioridade, data_abertura, descricao_problema",
                "'OS-2025-004', " + ids.get("joao") + ", " + ids.get("area_a") + ", " + ids.get("os_elet") + ", " +
                        ids.get("eq_elet") + ", " + ids.get("st_concl") + ", 3, '2025-04-05 09:00:00', 'Manutenção preventiva'"));

        ids.put("os5", insert(conn, "ordem_servico",
                "numero_sequencial, solicitante_id, area_campus_id, tipo_os_id, equipe_id, status_id, prioridade, data_abertura, descricao_problema",
                "'OS-2025-005', " + ids.get("joao") + ", " + ids.get("area_b") + ", " + ids.get("os_marc") + ", " +
                        ids.get("eq_elet") + ", " + ids.get("st_concl") + ", 2, '2025-05-12 11:00:00', 'Conserto de armário'"));

        ids.put("os6", insert(conn, "ordem_servico",
                "numero_sequencial, solicitante_id, area_campus_id, tipo_os_id, equipe_id, status_id, prioridade, data_abertura, descricao_problema",
                "'OS-2025-006', " + ids.get("joao") + ", " + ids.get("area_bib") + ", " + ids.get("os_elet") + ", " +
                        ids.get("eq_elet") + ", " + ids.get("st_aberta") + ", 1, '2025-12-01 08:00:00', 'Troca de lâmpadas'"));

        ids.put("os7", insert(conn, "ordem_servico",
                "numero_sequencial, solicitante_id, area_campus_id, tipo_os_id, equipe_id, status_id, prioridade, data_abertura, descricao_problema",
                "'OS-2025-007', " + ids.get("carlos") + ", " + ids.get("area_gin") + ", " + ids.get("os_hidra") + ", " +
                        ids.get("eq_hidra") + ", " + ids.get("st_atend") + ", 2, '2025-12-05 09:30:00', 'Reparo no bebedouro'"));

        System.out.println("  ✓ Ordens de serviço inseridas");

        // 11. Andamentos
        insert(conn, "andamento_ordem_servico", "os_id, funcionario_id, status_novo_id, data_hora, descricao",
                ids.get("os1") + ", " + ids.get("func_maria") + ", " + ids.get("st_aberta") + ", '2025-01-10 08:30:00', 'OS aberta'");
        insert(conn, "andamento_ordem_servico", "os_id, funcionario_id, status_novo_id, data_hora, descricao",
                ids.get("os1") + ", " + ids.get("func_maria") + ", " + ids.get("st_atend") + ", '2025-01-11 09:00:00', 'Equipe iniciou atendimento'");
        insert(conn, "andamento_ordem_servico", "os_id, funcionario_id, status_novo_id, data_hora, descricao",
                ids.get("os2") + ", " + ids.get("func_pedro") + ", " + ids.get("st_atend") + ", '2025-02-15 10:30:00', 'Diagnóstico realizado'");
        insert(conn, "andamento_ordem_servico", "os_id, funcionario_id, status_novo_id, data_hora, descricao",
                ids.get("os2") + ", " + ids.get("func_pedro") + ", " + ids.get("st_aguard") + ", '2025-02-16 14:00:00', 'Aguardando material'");
        insert(conn, "andamento_ordem_servico", "os_id, funcionario_id, status_novo_id, data_hora, descricao",
                ids.get("os3") + ", " + ids.get("func_ana") + ", " + ids.get("st_aguard") + ", '2025-03-21 08:00:00', 'Material solicitado'");
        insert(conn, "andamento_ordem_servico", "os_id, funcionario_id, status_novo_id, data_hora, descricao",
                ids.get("os6") + ", " + ids.get("func_maria") + ", " + ids.get("st_aberta") + ", '2025-12-01 08:00:00', 'Solicitação recebida'");
        insert(conn, "andamento_ordem_servico", "os_id, funcionario_id, status_novo_id, data_hora, descricao",
                ids.get("os6") + ", " + ids.get("func_maria") + ", " + ids.get("st_atend") + ", '2025-12-02 10:00:00', 'Verificando local'");
        insert(conn, "andamento_ordem_servico", "os_id, funcionario_id, status_novo_id, data_hora, descricao",
                ids.get("os7") + ", " + ids.get("func_pedro") + ", " + ids.get("st_atend") + ", '2025-12-05 09:45:00', 'Iniciado reparo'");
        System.out.println("  ✓ Andamentos inseridos");

        // 12-18. Materiais
        ids.put("cat_elet", insert(conn, "categoria_material", "nome", "'Elétrica'"));
        ids.put("cat_hidra", insert(conn, "categoria_material", "nome", "'Hidráulica'"));
        ids.put("cat_pint", insert(conn, "categoria_material", "nome", "'Pintura'"));
        insert(conn, "categoria_material", "nome", "'Marcenaria'");
        System.out.println("  ✓ Categorias de material inseridas");

        ids.put("un_un", insert(conn, "unidade_medida", "sigla, descricao", "'UN', 'Unidade'"));
        ids.put("un_m", insert(conn, "unidade_medida", "sigla, descricao", "'M', 'Metro'"));
        ids.put("un_l", insert(conn, "unidade_medida", "sigla, descricao", "'L', 'Litro'"));
        insert(conn, "unidade_medida", "sigla, descricao", "'KG', 'Quilograma'");
        System.out.println("  ✓ Unidades de medida inseridas");

        ids.put("marca_philips", insert(conn, "marca", "nome", "'Philips'"));
        ids.put("marca_tigre", insert(conn, "marca", "nome", "'Tigre'"));
        ids.put("marca_suvinil", insert(conn, "marca", "nome", "'Suvinil'"));
        ids.put("marca_3m", insert(conn, "marca", "nome", "'3M'"));
        System.out.println("  ✓ Marcas inseridas");

        ids.put("prod_lamp", insert(conn, "produto", "descricao, categoria_id, unidade_medida_id, marca_id",
                "'Lâmpada LED 9W', " + ids.get("cat_elet") + ", " + ids.get("un_un") + ", " + ids.get("marca_philips")));
        ids.put("prod_fio", insert(conn, "produto", "descricao, categoria_id, unidade_medida_id, marca_id",
                "'Fio elétrico 2.5mm', " + ids.get("cat_elet") + ", " + ids.get("un_m") + ", " + ids.get("marca_3m")));
        ids.put("prod_cano", insert(conn, "produto", "descricao, categoria_id, unidade_medida_id, marca_id",
                "'Cano PVC 25mm', " + ids.get("cat_hidra") + ", " + ids.get("un_m") + ", " + ids.get("marca_tigre")));
        ids.put("prod_tinta", insert(conn, "produto", "descricao, categoria_id, unidade_medida_id, marca_id",
                "'Tinta látex', " + ids.get("cat_pint") + ", " + ids.get("un_l") + ", " + ids.get("marca_suvinil")));
        ids.put("prod_torn", insert(conn, "produto", "descricao, categoria_id, unidade_medida_id, marca_id",
                "'Torneira', " + ids.get("cat_hidra") + ", " + ids.get("un_un") + ", " + ids.get("marca_tigre")));
        ids.put("prod_inter", insert(conn, "produto", "descricao, categoria_id, unidade_medida_id, marca_id",
                "'Interruptor simples', " + ids.get("cat_elet") + ", " + ids.get("un_un") + ", " + ids.get("marca_3m")));
        System.out.println("  ✓ Produtos inseridos");

        ids.put("cor_branco", insert(conn, "cor", "nome", "'Branco'"));
        ids.put("cor_azul", insert(conn, "cor", "nome", "'Azul'"));
        insert(conn, "cor", "nome", "'Preto'");
        ids.put("cor_crom", insert(conn, "cor", "nome", "'Cromado'"));
        System.out.println("  ✓ Cores inseridas");

        ids.put("tam_padrao", insert(conn, "tamanho", "descricao", "'Padrão'"));
        ids.put("tam_25mm", insert(conn, "tamanho", "descricao", "'25mm'"));
        ids.put("tam_18l", insert(conn, "tamanho", "descricao", "'18L'"));
        System.out.println("  ✓ Tamanhos inseridos");

        ids.put("var_lamp", insert(conn, "produto_variacao", "produto_id, cor_id, tamanho_id, codigo_interno",
                ids.get("prod_lamp") + ", " + ids.get("cor_branco") + ", " + ids.get("tam_padrao") + ", 'LED-9W-BCO'"));
        ids.put("var_fio", insert(conn, "produto_variacao", "produto_id, cor_id, tamanho_id, codigo_interno",
                ids.get("prod_fio") + ", " + ids.get("cor_azul") + ", " + ids.get("tam_padrao") + ", 'FIO-2.5-AZ'"));
        ids.put("var_cano", insert(conn, "produto_variacao", "produto_id, cor_id, tamanho_id, codigo_interno",
                ids.get("prod_cano") + ", " + ids.get("cor_branco") + ", " + ids.get("tam_25mm") + ", 'PVC-25-BCO'"));
        ids.put("var_tinta", insert(conn, "produto_variacao", "produto_id, cor_id, tamanho_id, codigo_interno",
                ids.get("prod_tinta") + ", " + ids.get("cor_branco") + ", " + ids.get("tam_18l") + ", 'TINTA-18L-BCO'"));
        ids.put("var_torn", insert(conn, "produto_variacao", "produto_id, cor_id, tamanho_id, codigo_interno",
                ids.get("prod_torn") + ", " + ids.get("cor_crom") + ", " + ids.get("tam_padrao") + ", 'TORN-METAL'"));
        ids.put("var_inter", insert(conn, "produto_variacao", "produto_id, cor_id, tamanho_id, codigo_interno",
                ids.get("prod_inter") + ", " + ids.get("cor_branco") + ", " + ids.get("tam_padrao") + ", 'INT-SIMPLES'"));
        System.out.println("  ✓ Variações de produto inseridas");

        // 19. Locais de Estoque
        ids.put("loc_central", insert(conn, "local_estoque", "descricao, responsavel_id",
                "'Almoxarifado Central', " + ids.get("func_maria")));
        ids.put("loc_bloco_b", insert(conn, "local_estoque", "descricao, responsavel_id",
                "'Depósito Bloco B', " + ids.get("func_pedro")));
        System.out.println("  ✓ Locais de estoque inseridos");

        // 20. Estoque (não precisa RETURNING)
        execute(conn, "INSERT INTO estoque (produto_variacao_id, local_estoque_id, quantidade, ponto_reposicao) VALUES " +
                "(" + ids.get("var_lamp") + ", " + ids.get("loc_central") + ", 5, 20), " +
                "(" + ids.get("var_fio") + ", " + ids.get("loc_central") + ", 80, 100), " +
                "(" + ids.get("var_cano") + ", " + ids.get("loc_central") + ", 15, 50), " +
                "(" + ids.get("var_tinta") + ", " + ids.get("loc_bloco_b") + ", 10, 5), " +
                "(" + ids.get("var_torn") + ", " + ids.get("loc_central") + ", 3, 10), " +
                "(" + ids.get("var_inter") + ", " + ids.get("loc_central") + ", 25, 30)");
        System.out.println("  ✓ Estoque inserido");

        // 21. Tipos de Movimento
        insert(conn, "tipo_movimento_estoque", "descricao, sinal", "'Entrada', '+'");
        ids.put("mov_saida", insert(conn, "tipo_movimento_estoque", "descricao, sinal", "'Saída', '-'"));
        insert(conn, "tipo_movimento_estoque", "descricao, sinal", "'Devolução', '+'");
        insert(conn, "tipo_movimento_estoque", "descricao, sinal", "'Ajuste', '+'");
        System.out.println("  ✓ Tipos de movimento de estoque inseridos");

        // 22. Movimentos de Estoque
        execute(conn, "INSERT INTO movimento_estoque " +
                "(produto_variacao_id, local_estoque_id, tipo_movimento_id, quantidade, data_hora, funcionario_id, ordem_servico_id, observacao) VALUES " +
                "(" + ids.get("var_lamp") + ", " + ids.get("loc_central") + ", " + ids.get("mov_saida") + ", 10, '2025-02-15 11:00:00', " + ids.get("func_maria") + ", " + ids.get("os2") + ", 'Uso na OS-2025-002'), " +
                "(" + ids.get("var_fio") + ", " + ids.get("loc_central") + ", " + ids.get("mov_saida") + ", 50, '2025-02-15 11:30:00', " + ids.get("func_maria") + ", " + ids.get("os2") + ", 'Uso na OS-2025-002'), " +
                "(" + ids.get("var_cano") + ", " + ids.get("loc_central") + ", " + ids.get("mov_saida") + ", 20, '2025-03-21 08:00:00', " + ids.get("func_ana") + ", " + ids.get("os3") + ", 'Uso na OS-2025-003'), " +
                "(" + ids.get("var_lamp") + ", " + ids.get("loc_central") + ", " + ids.get("mov_saida") + ", 15, '2025-04-05 10:00:00', " + ids.get("func_maria") + ", " + ids.get("os4") + ", 'Uso na OS-2025-004'), " +
                "(" + ids.get("var_torn") + ", " + ids.get("loc_central") + ", " + ids.get("mov_saida") + ", 5, '2025-02-16 09:00:00', " + ids.get("func_pedro") + ", " + ids.get("os2") + ", 'Uso na OS-2025-002'), " +
                "(" + ids.get("var_inter") + ", " + ids.get("loc_central") + ", " + ids.get("mov_saida") + ", 8, '2025-01-11 10:00:00', " + ids.get("func_maria") + ", " + ids.get("os1") + ", 'Uso na OS-2025-001')");
        System.out.println("  ✓ Movimentos de estoque inseridos");
    }

    private int insert(Connection conn, String table, String columns, String values) throws SQLException {
        String sql = "INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ") RETURNING id";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("Falha ao obter ID gerado");
        }
    }

    private void execute(Connection conn, String sql) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }
}