package br.ifce.sigej.controller;

import br.ifce.sigej.dao.EquipeMembroDAO;
import br.ifce.sigej.dao.EquipeManutencaoDAO;
import br.ifce.sigej.dao.FuncionarioDAO;
import br.ifce.sigej.model.EquipeMembro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/equipe-membros")
public class EquipeMembroController {

    @Autowired
    private EquipeMembroDAO equipeMembroDAO;

    @Autowired
    private EquipeManutencaoDAO equipeManutencaoDAO;

    @Autowired
    private FuncionarioDAO funcionarioDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("membros", equipeMembroDAO.listar());
        return "equipemembro/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("membro", new EquipeMembro());
        model.addAttribute("equipes", equipeManutencaoDAO.listar());
        model.addAttribute("funcionarios", funcionarioDAO.listar());
        return "equipemembro/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return equipeMembroDAO.buscarPorId(id)
                .map(membro -> {
                    model.addAttribute("membro", membro);
                    model.addAttribute("equipes", equipeManutencaoDAO.listar());
                    model.addAttribute("funcionarios", funcionarioDAO.listar());
                    return "equipemembro/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Membro não encontrado!");
                    return "redirect:/equipe-membros";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute EquipeMembro membro, RedirectAttributes redirectAttributes) {
        try {
            if (membro.getId() == 0) {
                equipeMembroDAO.inserir(membro);
                redirectAttributes.addFlashAttribute("sucesso", "Membro cadastrado com sucesso!");
            } else {
                equipeMembroDAO.atualizar(membro);
                redirectAttributes.addFlashAttribute("sucesso", "Membro atualizado com sucesso!");
            }
            return "redirect:/equipe-membros";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/equipe-membros/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            equipeMembroDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Membro excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/equipe-membros";
    }
}