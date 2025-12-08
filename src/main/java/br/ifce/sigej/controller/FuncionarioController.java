package br.ifce.sigej.controller;

import br.ifce.sigej.dao.FuncionarioDAO;
import br.ifce.sigej.dao.PessoaDAO;
import br.ifce.sigej.dao.SetorDAO;
import br.ifce.sigej.dao.TipoFuncionarioDAO;
import br.ifce.sigej.model.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioDAO funcionarioDAO;

    @Autowired
    private PessoaDAO pessoaDAO;

    @Autowired
    private TipoFuncionarioDAO tipoFuncionarioDAO;

    @Autowired
    private SetorDAO setorDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("funcionarios", funcionarioDAO.listar());
        model.addAttribute("pessoas", pessoaDAO.listar());
        model.addAttribute("tipos", tipoFuncionarioDAO.listar());
        model.addAttribute("setores", setorDAO.listar());
        return "funcionario/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("pessoas", pessoaDAO.listar());
        model.addAttribute("tipos", tipoFuncionarioDAO.listar());
        model.addAttribute("setores", setorDAO.listar());
        return "funcionario/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return funcionarioDAO.buscarPorId(id)
                .map(funcionario -> {
                    model.addAttribute("funcionario", funcionario);
                    model.addAttribute("pessoas", pessoaDAO.listar());
                    model.addAttribute("tipos", tipoFuncionarioDAO.listar());
                    model.addAttribute("setores", setorDAO.listar());
                    return "funcionario/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Funcionário não encontrado!");
                    return "redirect:/funcionarios";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Funcionario funcionario, RedirectAttributes redirectAttributes) {
        try {
            if (funcionario.getId() == 0) {
                funcionarioDAO.inserir(funcionario);
                redirectAttributes.addFlashAttribute("sucesso", "Funcionário cadastrado com sucesso!");
            } else {
                funcionarioDAO.atualizar(funcionario);
                redirectAttributes.addFlashAttribute("sucesso", "Funcionário atualizado com sucesso!");
            }
            return "redirect:/funcionarios";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/funcionarios/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            funcionarioDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Funcionário excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/funcionarios";
    }
}