package br.ifce.sigej.controller;

import br.ifce.sigej.dao.CorDAO;
import br.ifce.sigej.model.Cor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cores")
public class CorController {

    @Autowired
    private CorDAO corDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cores", corDAO.listar());
        return "cor/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cor", new Cor());
        return "cor/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return corDAO.buscarPorId(id)
                .map(cor -> {
                    model.addAttribute("cor", cor);
                    return "cor/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Cor não encontrada!");
                    return "redirect:/cores";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cor cor, RedirectAttributes redirectAttributes) {
        try {
            if (cor.getId() == 0) {
                corDAO.inserir(cor);
                redirectAttributes.addFlashAttribute("sucesso", "Cor cadastrada com sucesso!");
            } else {
                corDAO.atualizar(cor);
                redirectAttributes.addFlashAttribute("sucesso", "Cor atualizada com sucesso!");
            }
            return "redirect:/cores";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/cores/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            corDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Cor excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/cores";
    }
}