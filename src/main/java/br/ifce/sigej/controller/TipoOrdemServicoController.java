package br.ifce.sigej.controller;

import br.ifce.sigej.dao.TipoOrdemServicoDAO;
import br.ifce.sigej.model.TipoOrdemServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tipos-ordem-servico")
public class TipoOrdemServicoController {

    @Autowired
    private TipoOrdemServicoDAO tipoOrdemServicoDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tiposOS", tipoOrdemServicoDAO.listar());
        return "tipoordemservico/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("tipoOS", new TipoOrdemServico());
        return "tipoordemservico/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return tipoOrdemServicoDAO.buscarPorId(id)
                .map(tipoOS -> {
                    model.addAttribute("tipoOS", tipoOS);
                    return "tipoordemservico/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Tipo de Ordem de Serviço não encontrado!");
                    return "redirect:/tipos-ordem-servico";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute TipoOrdemServico tipoOS, RedirectAttributes redirectAttributes) {
        try {
            if (tipoOS.getId() == 0) {
                tipoOrdemServicoDAO.inserir(tipoOS);
                redirectAttributes.addFlashAttribute("sucesso", "Tipo de OS cadastrado com sucesso!");
            } else {
                tipoOrdemServicoDAO.atualizar(tipoOS);
                redirectAttributes.addFlashAttribute("sucesso", "Tipo de OS atualizado com sucesso!");
            }
            return "redirect:/tipos-ordem-servico";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/tipos-ordem-servico/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            tipoOrdemServicoDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Tipo de OS excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/tipos-ordem-servico";
    }
}