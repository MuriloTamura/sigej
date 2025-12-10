package br.ifce.sigej.controller;

import br.ifce.sigej.dao.TipoMovimentoEstoqueDAO;
import br.ifce.sigej.model.TipoMovimentoEstoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tipos-movimento-estoque")
public class TipoMovimentoEstoqueController {

    @Autowired
    private TipoMovimentoEstoqueDAO tipoMovimentoEstoqueDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tiposMovimento", tipoMovimentoEstoqueDAO.listar());
        return "tipomovimentoestoque/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("tipoMovimento", new TipoMovimentoEstoque());
        return "tipomovimentoestoque/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return tipoMovimentoEstoqueDAO.buscarPorId(id)
                .map(tipoMovimento -> {
                    model.addAttribute("tipoMovimento", tipoMovimento);
                    return "tipomovimentoestoque/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Tipo de Movimento não encontrado!");
                    return "redirect:/tipos-movimento-estoque";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute TipoMovimentoEstoque tipoMovimento, RedirectAttributes redirectAttributes) {
        try {
            if (tipoMovimento.getId() == 0) {
                tipoMovimentoEstoqueDAO.inserir(tipoMovimento);
                redirectAttributes.addFlashAttribute("sucesso", "Tipo de Movimento cadastrado com sucesso!");
            } else {
                tipoMovimentoEstoqueDAO.atualizar(tipoMovimento);
                redirectAttributes.addFlashAttribute("sucesso", "Tipo de Movimento atualizado com sucesso!");
            }
            return "redirect:/tipos-movimento-estoque";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/tipos-movimento-estoque/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            tipoMovimentoEstoqueDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Tipo de Movimento excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/tipos-movimento-estoque";
    }
}