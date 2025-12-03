package br.ifce.sigej;

import br.ifce.sigej.dao.ItemOrdemServicoDAO;
import br.ifce.sigej.model.ItemOrdemServico;

import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {

        ItemOrdemServicoDAO itemDAO = new ItemOrdemServicoDAO();

// Inserir item (exemplo: para a OS 1 e produto_variacao 4)
        itemDAO.inserir(new ItemOrdemServico(
                1,   // os_id
                4,   // produto_variacao_id
                5.0, // quantidade prevista
                3.0  // quantidade usada
        ));

        System.out.println("\n=== ITENS DE ORDEM DE SERVIÇO ===");
        itemDAO.listar().forEach(i ->
                System.out.println(
                        i.getId() + " | OS=" + i.getOsId() +
                                " | ProdutoVar=" + i.getProdutoVariacaoId() +
                                " | Previsto=" + i.getQuantidadePrevista() +
                                " | Usado=" + i.getQuantidadeUsada()
                )
        );



    }
}
