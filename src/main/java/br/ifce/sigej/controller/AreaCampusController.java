package br.ifce.sigej.controller;

import br.ifce.sigej.dao.AreaCampusDAO;
import br.ifce.sigej.dao.TipoAreaCampusDAO;
import br.ifce.sigej.model.AreaCampus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/areas-campus")
public class AreaCampusController {

    @Autowired
    private AreaCampusDAO areaCampusDAO;

    @Autowired
    private TipoAreaCampusDAO tipoAreaCampusDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("areasCampus", areaCampusDAO.listar());
        return "areacampus/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("areaCampus", new AreaCampus());
        model.addAttribute("tiposArea", tipoAreaCampusDAO.listar());
        return "areacampus/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return areaCampusDAO.buscarPorId(id)
                .map(areaCampus -> {
                    model.addAttribute("areaCampus", areaCampus);
                    model.addAttribute("tiposArea", tipoAreaCampusDAO.listar());
                    return "areacampus/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Área do Campus não encontrada!");
                    return "redirect:/areas-campus";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute AreaCampus areaCampus, RedirectAttributes redirectAttributes) {
        try {
            if (areaCampus.getId() == 0) {
                areaCampusDAO.inserir(areaCampus);
                redirectAttributes.addFlashAttribute("sucesso", "Área do Campus cadastrada com sucesso!");
            } else {
                areaCampusDAO.atualizar(areaCampus);
                redirectAttributes.addFlashAttribute("sucesso", "Área do Campus atualizada com sucesso!");
            }
            return "redirect:/areas-campus";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/areas-campus/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            areaCampusDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Área do Campus excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/areas-campus";
    }
}