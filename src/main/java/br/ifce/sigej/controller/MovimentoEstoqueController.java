package br.ifce.sigej.controller;

import br.ifce.sigej.dao.MovimentoEstoqueDAO;
import br.ifce.sigej.dao.ProdutoVariacaoDAO;
import br.ifce.sigej.dao.LocalEstoqueDAO;
import br.ifce.sigej.dao.TipoMovimentoEstoqueDAO;
import br.ifce.sigej.dao.FuncionarioDAO;
import br.ifce.sigej.model.MovimentoEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movimento-estoque")
public class MovimentoEstoqueController {

    @Autowired
    private MovimentoEstoqueDAO movimentoEstoqueDAO;

    @Autowired
    private ProdutoVariacaoDAO produtoVariacaoDAO;

    @Autowired
    private LocalEstoqueDAO localEstoqueDAO;

    @Autowired
    private TipoMovimentoEstoqueDAO tipoMovimentoEstoqueDAO;

    @Autowired
    private FuncionarioDAO funcionarioDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("movimentos", movimentoEstoqueDAO.listar());
        return "movimentoestoque/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("movimento", new MovimentoEstoque());
        carregarDadosFormulario(model);
        return "movimentoestoque/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return movimentoEstoqueDAO.buscarPorId(id)
                .map(movimento -> {
                    model.addAttribute("movimento", movimento);
                    carregarDadosFormulario(model);
                    return "movimentoestoque/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Movimento não encontrado!");
                    return "redirect:/movimento-estoque";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute MovimentoEstoque movimento, RedirectAttributes redirectAttributes) {
        try {
            if (movimento.getId() == 0) {
                movimentoEstoqueDAO.inserir(movimento);
                redirectAttributes.addFlashAttribute("sucesso", "Movimento cadastrado com sucesso!");
            } else {
                movimentoEstoqueDAO.atualizar(movimento);
                redirectAttributes.addFlashAttribute("sucesso", "Movimento atualizado com sucesso!");
            }
            return "redirect:/movimento-estoque";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/movimento-estoque/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            movimentoEstoqueDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Movimento excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/movimento-estoque";
    }

    // Método auxiliar para carregar dados do formulário com nomes
    private void carregarDadosFormulario(Model model) {
        // Usa listarComDetalhes() para trazer produto/cor/tamanho
        model.addAttribute("variacoes", produtoVariacaoDAO.listarComDetalhes());

        // Locais já tem descrição
        model.addAttribute("locais", localEstoqueDAO.listar());

        // Tipos já tem descrição
        model.addAttribute("tipos", tipoMovimentoEstoqueDAO.listar());

        // Usa listarComNomes() para trazer nome da pessoa
        model.addAttribute("funcionarios", funcionarioDAO.listarComNomes());
    }
}