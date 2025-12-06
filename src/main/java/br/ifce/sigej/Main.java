package br.ifce.sigej;


import br.ifce.sigej.dao.AndamentoOrdemServicoDAO;
import br.ifce.sigej.dao.CategoriaMaterialDAO;
import br.ifce.sigej.model.AndamentoOrdemServico;
import br.ifce.sigej.model.CategoriaMaterial;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) {

        CategoriaMaterialDAO catDAO = new CategoriaMaterialDAO();

        catDAO.inserir(new CategoriaMaterial("Jardinagem"));
        catDAO.inserir(new CategoriaMaterial("Limpeza"));

        System.out.println("\nANTES DO UPDATE");
        catDAO.listar().forEach(c -> System.out.println(c.getId() + " - " + c.getNome()));

// Atualizar
        CategoriaMaterial c = new CategoriaMaterial("Limpeza Geral");
        c.setId(2);
        catDAO.atualizar(c);

// Deletar
        catDAO.deletar(1);

        System.out.println("\nDEPOIS DO UPDATE e DELETE");
        catDAO.listar().forEach(d -> System.out.println(c.getId() + " - " + c.getNome()));



    }
}
