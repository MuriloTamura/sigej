package br.ifce.sigej.controller;

import br.ifce.sigej.dao.TipoAreaCampusDAO;
import br.ifce.sigej.model.TipoAreaCampus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tipos-area-campus")
public class TipoAreaCampusController {

    @Autowired
    private TipoAreaCampusDAO tipoAreaCampusDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tipos", tipoAreaCampusDAO.listar());
        return "tipoareacampus/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("tipo", new TipoAreaCampus());
        return "tipoareacampus/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return tipoAreaCampusDAO.buscarPorId(id)
                .map(tipo -> {
                    model.addAttribute("tipo", tipo);
                    return "tipoareacampus/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Tipo de área do campus não encontrado!");
                    return "redirect:/tipos-area-campus";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute TipoAreaCampus tipo, RedirectAttributes redirectAttributes) {
        try {
            if (tipo.getId() == 0) {
                tipoAreaCampusDAO.inserir(tipo);
                redirectAttributes.addFlashAttribute("sucesso", "Tipo de área do campus cadastrado com sucesso!");
            } else {
                tipoAreaCampusDAO.atualizar(tipo);
                redirectAttributes.addFlashAttribute("sucesso", "Tipo de área do campus atualizado com sucesso!");
            }
            return "redirect:/tipos-area-campus";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/tipos-area-campus/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            tipoAreaCampusDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Tipo de área do campus excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/tipos-area-campus";
    }
}