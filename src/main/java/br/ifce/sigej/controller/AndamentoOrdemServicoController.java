package br.ifce.sigej.controller;

import br.ifce.sigej.dao.AndamentoOrdemServicoDAO;
import br.ifce.sigej.dao.OrdemServicoDAO;
import br.ifce.sigej.dao.StatusOrdemServicoDAO;
import br.ifce.sigej.dao.FuncionarioDAO;
import br.ifce.sigej.model.AndamentoOrdemServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/andamentos-os")
public class AndamentoOrdemServicoController {

    @Autowired
    private AndamentoOrdemServicoDAO andamentoOrdemServicoDAO;

    @Autowired
    private OrdemServicoDAO ordemServicoDAO;

    @Autowired
    private StatusOrdemServicoDAO statusOrdemServicoDAO;

    @Autowired
    private FuncionarioDAO funcionarioDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("andamentos", andamentoOrdemServicoDAO.listar());
        return "andamentoordemservico/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("andamento", new AndamentoOrdemServico());
        model.addAttribute("ordens", ordemServicoDAO.listar());
        model.addAttribute("status", statusOrdemServicoDAO.listar());
        model.addAttribute("funcionarios", funcionarioDAO.listar());
        return "andamentoordemservico/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return andamentoOrdemServicoDAO.buscarPorId(id)
                .map(andamento -> {
                    model.addAttribute("andamento", andamento);
                    model.addAttribute("ordens", ordemServicoDAO.listar());
                    model.addAttribute("status", statusOrdemServicoDAO.listar());
                    model.addAttribute("funcionarios", funcionarioDAO.listar());
                    return "andamentoordemservico/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Andamento não encontrado!");
                    return "redirect:/andamentos-os";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute AndamentoOrdemServico andamento, RedirectAttributes redirectAttributes) {
        try {
            if (andamento.getId() == 0) {
                andamentoOrdemServicoDAO.inserir(andamento);
                redirectAttributes.addFlashAttribute("sucesso", "Andamento cadastrado com sucesso!");
            } else {
                andamentoOrdemServicoDAO.atualizar(andamento);
                redirectAttributes.addFlashAttribute("sucesso", "Andamento atualizado com sucesso!");
            }
            return "redirect:/andamentos-os";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/andamentos-os/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            andamentoOrdemServicoDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Andamento excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/andamentos-os";
    }
}