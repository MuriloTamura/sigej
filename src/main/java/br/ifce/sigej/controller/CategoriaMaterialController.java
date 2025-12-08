package br.ifce.sigej.controller;

import br.ifce.sigej.dao.CategoriaMaterialDAO;
import br.ifce.sigej.model.CategoriaMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaMaterialController {

    @Autowired
    private CategoriaMaterialDAO categoriaMaterialDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaMaterialDAO.listar());
        return "categoria/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("categoria", new CategoriaMaterial());
        return "categoria/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return categoriaMaterialDAO.buscarPorId(id)
                .map(categoria -> {
                    model.addAttribute("categoria", categoria);
                    return "categoria/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Categoria não encontrada!");
                    return "redirect:/categorias";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute CategoriaMaterial categoria, RedirectAttributes redirectAttributes) {
        try {
            if (categoria.getId() == 0) {
                categoriaMaterialDAO.inserir(categoria);
                redirectAttributes.addFlashAttribute("sucesso", "Categoria cadastrada com sucesso!");
            } else {
                categoriaMaterialDAO.atualizar(categoria);
                redirectAttributes.addFlashAttribute("sucesso", "Categoria atualizada com sucesso!");
            }
            return "redirect:/categorias";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/categorias/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            categoriaMaterialDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Categoria excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/categorias";
    }
}