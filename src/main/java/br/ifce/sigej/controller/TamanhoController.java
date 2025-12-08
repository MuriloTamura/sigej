package br.ifce.sigej.controller;

import br.ifce.sigej.dao.TamanhoDAO;
import br.ifce.sigej.model.Tamanho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tamanhos")
public class TamanhoController {

    @Autowired
    private TamanhoDAO tamanhoDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tamanhos", tamanhoDAO.listar());
        return "tamanho/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("tamanho", new Tamanho());
        return "tamanho/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return tamanhoDAO.buscarPorId(id)
                .map(tamanho -> {
                    model.addAttribute("tamanho", tamanho);
                    return "tamanho/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Tamanho não encontrado!");
                    return "redirect:/tamanhos";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Tamanho tamanho, RedirectAttributes redirectAttributes) {
        try {
            if (tamanho.getId() == 0) {
                tamanhoDAO.inserir(tamanho);
                redirectAttributes.addFlashAttribute("sucesso", "Tamanho cadastrado com sucesso!");
            } else {
                tamanhoDAO.atualizar(tamanho);
                redirectAttributes.addFlashAttribute("sucesso", "Tamanho atualizado com sucesso!");
            }
            return "redirect:/tamanhos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/tamanhos/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            tamanhoDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Tamanho excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/tamanhos";
    }
}