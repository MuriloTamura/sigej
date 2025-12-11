package br.ifce.sigej.controller;

import br.ifce.sigej.dao.ItemOrdemServicoDAO;
import br.ifce.sigej.dao.OrdemServicoDAO;
import br.ifce.sigej.dao.ProdutoVariacaoDAO;
import br.ifce.sigej.model.ItemOrdemServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/itens-ordem-servico")
public class ItemOrdemServicoController {

    @Autowired
    private ItemOrdemServicoDAO itemOrdemServicoDAO;

    @Autowired
    private OrdemServicoDAO ordemServicoDAO;

    @Autowired
    private ProdutoVariacaoDAO produtoVariacaoDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("itens", itemOrdemServicoDAO.listar());
        return "itemordemservico/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("item", new ItemOrdemServico());
        model.addAttribute("ordens", ordemServicoDAO.listar());
        model.addAttribute("variacoes", produtoVariacaoDAO.listar());
        return "itemordemservico/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return itemOrdemServicoDAO.buscarPorId(id)
                .map(item -> {
                    model.addAttribute("item", item);
                    model.addAttribute("ordens", ordemServicoDAO.listar());
                    model.addAttribute("variacoes", produtoVariacaoDAO.listar());
                    return "itemordemservico/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Item não encontrado!");
                    return "redirect:/itens-ordem-servico";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute ItemOrdemServico item, RedirectAttributes redirectAttributes) {
        try {
            if (item.getId() == 0) {
                itemOrdemServicoDAO.inserir(item);
                redirectAttributes.addFlashAttribute("sucesso", "Item cadastrado com sucesso!");
            } else {
                itemOrdemServicoDAO.atualizar(item);
                redirectAttributes.addFlashAttribute("sucesso", "Item atualizado com sucesso!");
            }
            return "redirect:/itens-ordem-servico";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/itens-ordem-servico/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            itemOrdemServicoDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Item excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/itens-ordem-servico";
    }
}