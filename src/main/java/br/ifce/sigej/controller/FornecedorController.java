package br.ifce.sigej.controller;

import br.ifce.sigej.dao.FornecedorDAO;
import br.ifce.sigej.model.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorDAO fornecedorDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("fornecedores", fornecedorDAO.listar());
        return "fornecedor/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "fornecedor/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return fornecedorDAO.buscarPorId(id)
                .map(fornecedor -> {
                    model.addAttribute("fornecedor", fornecedor);
                    return "fornecedor/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Fornecedor não encontrado!");
                    return "redirect:/fornecedores";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Fornecedor fornecedor, RedirectAttributes redirectAttributes) {
        try {
            if (fornecedor.getId() == 0) {
                fornecedorDAO.inserir(fornecedor);
                redirectAttributes.addFlashAttribute("sucesso", "Fornecedor cadastrado com sucesso!");
            } else {
                fornecedorDAO.atualizar(fornecedor);
                redirectAttributes.addFlashAttribute("sucesso", "Fornecedor atualizado com sucesso!");
            }
            return "redirect:/fornecedores";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/fornecedores/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            fornecedorDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Fornecedor excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/fornecedores";
    }
}