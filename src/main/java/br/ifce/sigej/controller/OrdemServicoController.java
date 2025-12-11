package br.ifce.sigej.controller;

import br.ifce.sigej.dao.*;
import br.ifce.sigej.model.OrdemServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoDAO ordemServicoDAO;

    @Autowired
    private PessoaDAO pessoaDAO;

    @Autowired
    private FuncionarioDAO funcionarioDAO;

    @Autowired
    private AreaCampusDAO areaCampusDAO;

    @Autowired
    private TipoOrdemServicoDAO tipoOrdemServicoDAO;

    @Autowired
    private EquipeManutencaoDAO equipeManutencaoDAO;

    @Autowired
    private StatusOrdemServicoDAO statusOrdemServicoDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ordens", ordemServicoDAO.listar());
        return "ordemservico/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("ordem", new OrdemServico());
        carregarDadosFormulario(model);
        return "ordemservico/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return ordemServicoDAO.buscarPorId(id)
                .map(ordem -> {
                    model.addAttribute("ordem", ordem);
                    carregarDadosFormulario(model);
                    return "ordemservico/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Ordem de serviço não encontrada!");
                    return "redirect:/ordens-servico";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute OrdemServico ordem, RedirectAttributes redirectAttributes) {
        try {
            if (ordem.getId() == 0) {
                ordemServicoDAO.inserir(ordem);
                redirectAttributes.addFlashAttribute("sucesso", "Ordem de serviço cadastrada com sucesso!");
            } else {
                ordemServicoDAO.atualizar(ordem);
                redirectAttributes.addFlashAttribute("sucesso", "Ordem de serviço atualizada com sucesso!");
            }
            return "redirect:/ordens-servico";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/ordens-servico/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            ordemServicoDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Ordem de serviço excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/ordens-servico";
    }

    // Método auxiliar para carregar dados do formulário
    private void carregarDadosFormulario(Model model) {
        model.addAttribute("pessoas", pessoaDAO.listar());
        model.addAttribute("funcionarios", funcionarioDAO.listarComNomes());  // Usa método com JOIN
        model.addAttribute("areas", areaCampusDAO.listar());
        model.addAttribute("tipos", tipoOrdemServicoDAO.listar());
        model.addAttribute("equipes", equipeManutencaoDAO.listar());
        model.addAttribute("status", statusOrdemServicoDAO.listar());
    }
}