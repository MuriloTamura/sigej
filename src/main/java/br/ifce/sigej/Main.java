package br.ifce.sigej;

import br.ifce.sigej.dao.TipoMovimentoEstoqueDAO;
import br.ifce.sigej.model.TipoMovimentoEstoque;

public class Main {
    public static void main(String[] args) {

        TipoMovimentoEstoqueDAO tDAO = new TipoMovimentoEstoqueDAO();

        // INSERIR MOVIMENTOS
        tDAO.inserir(new TipoMovimentoEstoque("Entrada", "+"));
        tDAO.inserir(new TipoMovimentoEstoque("Saída", "-"));

        // LISTAR
        System.out.println("\n=== TIPOS DE MOVIMENTO ===");
        tDAO.listar().forEach(t ->
                System.out.println(t.getId() + " | " + t.getDescricao() + " | sinal=" + t.getSinal())
        );
    }
}
