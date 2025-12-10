package br.ifce.sigej.controller;

import br.ifce.sigej.dao.EquipeManutencaoDAO;
import br.ifce.sigej.model.EquipeManutencao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/equipes-manutencao")
public class EquipeManutencaoController {

    @Autowired
    private EquipeManutencaoDAO equipeManutencaoDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("equipes", equipeManutencaoDAO.listar());
        return "equipemanutencao/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("equipe", new EquipeManutencao());
        return "equipemanutencao/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return equipeManutencaoDAO.buscarPorId(id)
                .map(equipe -> {
                    model.addAttribute("equipe", equipe);
                    return "equipemanutencao/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Equipe de manutenção não encontrada!");
                    return "redirect:/equipes-manutencao";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute EquipeManutencao equipe, RedirectAttributes redirectAttributes) {
        try {
            if (equipe.getId() == 0) {
                equipeManutencaoDAO.inserir(equipe);
                redirectAttributes.addFlashAttribute("sucesso", "Equipe de manutenção cadastrada com sucesso!");
            } else {
                equipeManutencaoDAO.atualizar(equipe);
                redirectAttributes.addFlashAttribute("sucesso", "Equipe de manutenção atualizada com sucesso!");
            }
            return "redirect:/equipes-manutencao";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/equipes-manutencao/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            equipeManutencaoDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Equipe de manutenção excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/equipes-manutencao";
    }
}