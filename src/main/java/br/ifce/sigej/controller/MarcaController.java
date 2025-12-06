package br.ifce.sigej.controller;

import br.ifce.sigej.dao.MarcaDAO;
import br.ifce.sigej.model.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/marcas")
public class MarcaController {

    @Autowired
    private MarcaDAO marcaDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("marcas", marcaDAO.listar());
        return "marca/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("marca", new Marca());
        return "marca/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return marcaDAO.buscarPorId(id)
                .map(marca -> {
                    model.addAttribute("marca", marca);
                    return "marca/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Marca não encontrada!");
                    return "redirect:/marcas";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Marca marca, RedirectAttributes redirectAttributes) {
        try {
            if (marca.getId() == 0) {
                marcaDAO.inserir(marca);
                redirectAttributes.addFlashAttribute("sucesso", "Marca cadastrada com sucesso!");
            } else {
                marcaDAO.atualizar(marca);
                redirectAttributes.addFlashAttribute("sucesso", "Marca atualizada com sucesso!");
            }
            return "redirect:/marcas";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/marcas/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            marcaDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Marca excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/marcas";
    }
}