package br.ifce.sigej.controller;

import br.ifce.sigej.dao.SetorDAO;
import br.ifce.sigej.model.Setor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/setores")
public class SetorController {

    @Autowired
    private SetorDAO SetorDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("setores", SetorDAO.listar());
        return "setor/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("setor", new Setor());
        return "setor/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return SetorDAO.buscarPorId(id)
                .map(setor -> {
                    model.addAttribute("setor", setor);
                    return "setor/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Setor não encontrado!");
                    return "redirect:/setores";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Setor setor, RedirectAttributes redirectAttributes) {
        try {
            if (setor.getId() == 0) {
                SetorDAO.inserir(setor);
                redirectAttributes.addFlashAttribute("sucesso", "Setor cadastrado com sucesso!");
            } else {
                SetorDAO.atualizar(setor);
                redirectAttributes.addFlashAttribute("sucesso", "Setor atualizado com sucesso!");
            }
            return "redirect:/setores";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/setores/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            SetorDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Setor excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/setores";
    }
}