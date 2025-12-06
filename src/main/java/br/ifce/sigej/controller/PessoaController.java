package br.ifce.sigej.controller;

import br.ifce.sigej.dao.PessoaDAO;
import br.ifce.sigej.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaDAO pessoaDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pessoas", pessoaDAO.listar());
        return "pessoa/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        return "pessoa/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return pessoaDAO.buscarPorId(id)
                .map(pessoa -> {
                    model.addAttribute("pessoa", pessoa);
                    return "pessoa/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Pessoa não encontrada!");
                    return "redirect:/pessoas";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Pessoa pessoa, RedirectAttributes redirectAttributes) {
        try {
            if (pessoa.getId() == 0) {
                pessoaDAO.inserir(pessoa);
                redirectAttributes.addFlashAttribute("sucesso", "Pessoa cadastrada com sucesso!");
            } else {
                pessoaDAO.atualizar(pessoa);
                redirectAttributes.addFlashAttribute("sucesso", "Pessoa atualizada com sucesso!");
            }
            return "redirect:/pessoas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/pessoas/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            pessoaDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Pessoa excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pessoas";
    }
}