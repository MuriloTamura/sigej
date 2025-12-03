package br.ifce.sigej.dao;

import br.ifce.sigej.database.ConnectionFactory;
import br.ifce.sigej.model.ItemOrdemServico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemOrdemServicoDAO {

    public void inserir(ItemOrdemServico item) {
        String sql = """
            INSERT INTO item_ordem_servico
            (os_id, produto_variacao_id, quantidade_prevista, quantidade_usada)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, item.getOsId());
            stmt.setObject(2, item.getProdutoVariacaoId());
            stmt.setObject(3, item.getQuantidadePrevista());
            stmt.setObject(4, item.getQuantidadeUsada());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao inserir item_os: " + e.getMessage());
        }
    }

    public List<ItemOrdemServico> listar() {
        List<ItemOrdemServico> lista = new ArrayList<>();

        String sql = "SELECT * FROM item_ordem_servico ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ItemOrdemServico item = new ItemOrdemServico();

                item.setId(rs.getInt("id"));
                item.setOsId((Integer) rs.getObject("os_id"));
                item.setProdutoVariacaoId((Integer) rs.getObject("produto_variacao_id"));

                item.setQuantidadePrevista(
                        rs.getBigDecimal("quantidade_prevista") != null ?
                                rs.getBigDecimal("quantidade_prevista").doubleValue() : null
                );

                item.setQuantidadeUsada(
                        rs.getBigDecimal("quantidade_usada") != null ?
                                rs.getBigDecimal("quantidade_usada").doubleValue() : null
                );

                lista.add(item);
            }


        } catch (Exception e) {
            System.out.println("Erro ao listar item_os: " + e.getMessage());
        }

        return lista;
    }
}
