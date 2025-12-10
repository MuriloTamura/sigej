package br.ifce.sigej.controller;

import br.ifce.sigej.dao.LocalEstoqueDAO;
import br.ifce.sigej.dao.FuncionarioDAO;
import br.ifce.sigej.model.LocalEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/locais-estoque")
public class LocalEstoqueController {

    @Autowired
    private LocalEstoqueDAO localEstoqueDAO;

    @Autowired
    private FuncionarioDAO funcionarioDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("locaisEstoque", localEstoqueDAO.listar());
        return "localestoque/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("localEstoque", new LocalEstoque());
        model.addAttribute("funcionarios", funcionarioDAO.listar());
        return "localestoque/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return localEstoqueDAO.buscarPorId(id)
                .map(localEstoque -> {
                    model.addAttribute("localEstoque", localEstoque);
                    model.addAttribute("funcionarios", funcionarioDAO.listar());
                    return "localestoque/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Local de Estoque não encontrado!");
                    return "redirect:/locais-estoque";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute LocalEstoque localEstoque, RedirectAttributes redirectAttributes) {
        try {
            if (localEstoque.getId() == 0) {
                localEstoqueDAO.inserir(localEstoque);
                redirectAttributes.addFlashAttribute("sucesso", "Local de Estoque cadastrado com sucesso!");
            } else {
                localEstoqueDAO.atualizar(localEstoque);
                redirectAttributes.addFlashAttribute("sucesso", "Local de Estoque atualizado com sucesso!");
            }
            return "redirect:/locais-estoque";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/locais-estoque/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            localEstoqueDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Local de Estoque excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/locais-estoque";
    }
}