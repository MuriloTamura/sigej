package br.ifce.sigej.controller;

import br.ifce.sigej.dao.StatusOrdemServicoDAO;
import br.ifce.sigej.model.StatusOrdemServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/status-ordem-servico")
public class StatusOrdemServicoController {

    @Autowired
    private StatusOrdemServicoDAO statusOrdemServicoDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("statusList", statusOrdemServicoDAO.listar());
        return "statusordemservico/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("status", new StatusOrdemServico());
        return "statusordemservico/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return statusOrdemServicoDAO.buscarPorId(id)
                .map(status -> {
                    model.addAttribute("status", status);
                    return "statusordemservico/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Status não encontrado!");
                    return "redirect:/status-ordem-servico";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute StatusOrdemServico status, RedirectAttributes redirectAttributes) {
        try {
            if (status.getId() == 0) {
                statusOrdemServicoDAO.inserir(status);
                redirectAttributes.addFlashAttribute("sucesso", "Status cadastrado com sucesso!");
            } else {
                statusOrdemServicoDAO.atualizar(status);
                redirectAttributes.addFlashAttribute("sucesso", "Status atualizado com sucesso!");
            }
            return "redirect:/status-ordem-servico";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/status-ordem-servico/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            statusOrdemServicoDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Status excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/status-ordem-servico";
    }
}