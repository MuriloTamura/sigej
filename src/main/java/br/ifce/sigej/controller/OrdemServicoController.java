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
        model.addAttribute("pessoas", pessoaDAO.listar());
        model.addAttribute("funcionarios", funcionarioDAO.listar());
        model.addAttribute("areas", areaCampusDAO.listar());
        model.addAttribute("tipos", tipoOrdemServicoDAO.listar());
        model.addAttribute("equipes", equipeManutencaoDAO.listar());
        model.addAttribute("status", statusOrdemServicoDAO.listar());
        return "ordemservico/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return ordemServicoDAO.buscarPorId(id)
                .map(ordem -> {
                    model.addAttribute("ordem", ordem);
                    model.addAttribute("pessoas", pessoaDAO.listar());
                    model.addAttribute("funcionarios", funcionarioDAO.listar());
                    model.addAttribute("areas", areaCampusDAO.listar());
                    model.addAttribute("tipos", tipoOrdemServicoDAO.listar());
                    model.addAttribute("equipes", equipeManutencaoDAO.listar());
                    model.addAttribute("status", statusOrdemServicoDAO.listar());
                    return "ordemservico/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Ordem de serviço não encontrada!");
                    return "redirect:/ordens-servico";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute OrdemServico ordem, RedirectAttributes redirectAttributes, Model model) {
        try {
            System.out.println("=== INICIANDO SALVAMENTO ===");
            System.out.println("ID: " + ordem.getId());
            System.out.println("Número: " + ordem.getNumeroSequencial());
            System.out.println("Solicitante ID: " + ordem.getSolicitanteId());
            System.out.println("Área ID: " + ordem.getAreaCampusId());
            System.out.println("Tipo OS ID: " + ordem.getTipoOsId());
            System.out.println("Equipe ID: " + ordem.getEquipeId());
            System.out.println("Líder ID: " + ordem.getLiderId());
            System.out.println("Status ID: " + ordem.getStatusId());
            System.out.println("Prioridade: " + ordem.getPrioridade());
            System.out.println("Data Prevista: " + ordem.getDataPrevista());
            System.out.println("Descrição: " + ordem.getDescricaoProblema());

            if (ordem.getId() == 0) {
                System.out.println("Inserindo nova ordem...");
                ordemServicoDAO.inserir(ordem);
                System.out.println("Ordem inserida com sucesso!");
                redirectAttributes.addFlashAttribute("sucesso", "Ordem de serviço cadastrada com sucesso!");
            } else {
                System.out.println("Atualizando ordem existente...");
                ordemServicoDAO.atualizar(ordem);
                System.out.println("Ordem atualizada com sucesso!");
                redirectAttributes.addFlashAttribute("sucesso", "Ordem de serviço atualizada com sucesso!");
            }
            return "redirect:/ordens-servico";
        } catch (Exception e) {
            System.out.println("=== ERRO AO SALVAR ===");
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao salvar: " + e.getMessage());
            model.addAttribute("ordem", ordem);
            model.addAttribute("pessoas", pessoaDAO.listar());
            model.addAttribute("funcionarios", funcionarioDAO.listar());
            model.addAttribute("areas", areaCampusDAO.listar());
            model.addAttribute("tipos", tipoOrdemServicoDAO.listar());
            model.addAttribute("equipes", equipeManutencaoDAO.listar());
            model.addAttribute("status", statusOrdemServicoDAO.listar());
            return "ordemservico/form";
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
}