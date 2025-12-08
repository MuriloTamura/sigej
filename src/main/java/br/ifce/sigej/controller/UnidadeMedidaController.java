package br.ifce.sigej.controller;

import br.ifce.sigej.dao.UnidadeMedidaDAO;
import br.ifce.sigej.model.UnidadeMedida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/unidades-medida")
public class UnidadeMedidaController {

    @Autowired
    private UnidadeMedidaDAO unidadeMedidaDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("unidades", unidadeMedidaDAO.listar());
        return "unidade-medida/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("unidade", new UnidadeMedida());
        return "unidade-medida/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return unidadeMedidaDAO.buscarPorId(id)
                .map(unidade -> {
                    model.addAttribute("unidade", unidade);
                    return "unidade-medida/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Unidade de medida não encontrada!");
                    return "redirect:/unidades-medida";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute UnidadeMedida unidade, RedirectAttributes redirectAttributes) {
        try {
            if (unidade.getId() == 0) {
                unidadeMedidaDAO.inserir(unidade);
                redirectAttributes.addFlashAttribute("sucesso", "Unidade de medida cadastrada com sucesso!");
            } else {
                unidadeMedidaDAO.atualizar(unidade);
                redirectAttributes.addFlashAttribute("sucesso", "Unidade de medida atualizada com sucesso!");
            }
            return "redirect:/unidades-medida";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/unidades-medida/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            unidadeMedidaDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Unidade de medida excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/unidades-medida";
    }
}