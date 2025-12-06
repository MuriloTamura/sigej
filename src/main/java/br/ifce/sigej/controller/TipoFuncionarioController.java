package br.ifce.sigej.controller;

import br.ifce.sigej.dao.TipoFuncionarioDAO;
import br.ifce.sigej.model.TipoFuncionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tipos-funcionario")
public class TipoFuncionarioController {

    @Autowired
    private TipoFuncionarioDAO tipoFuncionarioDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tipos", tipoFuncionarioDAO.listar());
        return "tipo-funcionario/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("tipo", new TipoFuncionario());
        return "tipo-funcionario/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return tipoFuncionarioDAO.buscarPorId(id)
                .map(tipo -> {
                    model.addAttribute("tipo", tipo);
                    return "tipo-funcionario/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Tipo de funcionário não encontrado!");
                    return "redirect:/tipos-funcionario";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute TipoFuncionario tipo, RedirectAttributes redirectAttributes) {
        try {
            if (tipo.getId() == 0) {
                tipoFuncionarioDAO.inserir(tipo);
                redirectAttributes.addFlashAttribute("sucesso", "Tipo de funcionário cadastrado com sucesso!");
            } else {
                tipoFuncionarioDAO.atualizar(tipo);
                redirectAttributes.addFlashAttribute("sucesso", "Tipo de funcionário atualizado com sucesso!");
            }
            return "redirect:/tipos-funcionario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/tipos-funcionario/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            tipoFuncionarioDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Tipo de funcionário excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/tipos-funcionario";
    }
}