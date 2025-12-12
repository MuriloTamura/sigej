package br.ifce.sigej.controller;

import br.ifce.sigej.dao.RelatoriosDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/relatorios")
public class RelatoriosController {

    @Autowired
    private RelatoriosDAO relatoriosDAO;

    @GetMapping
    public String index() {
        return "relatorios/index";
    }

    @GetMapping("/os-abertas")
    public String osAbertas(Model model) {
        try {
            model.addAttribute("registros", relatoriosDAO.ordensServicoAbertas());
            model.addAttribute("titulo", "Ordens de Serviço em Aberto");
            return "relatorios/os-abertas";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao gerar relatório: " + e.getMessage());
            return "relatorios/index";
        }
    }

    @GetMapping("/materiais-baixo-estoque")
    public String materiaisBaixoEstoque(Model model) {
        try {
            model.addAttribute("registros", relatoriosDAO.materiaisAbaixoPontoReposicao());
            model.addAttribute("titulo", "Materiais Abaixo do Ponto de Reposição");
            return "relatorios/materiais-baixo-estoque";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao gerar relatório: " + e.getMessage());
            return "relatorios/index";
        }
    }

    @GetMapping("/timeline-os")
    public String timelineOS(@RequestParam(required = false) Integer osId, Model model) {
        try {
            if (osId != null) {
                model.addAttribute("registros", relatoriosDAO.timelineOrdemServico(osId));
                model.addAttribute("osId", osId);
            }
            model.addAttribute("titulo", "Timeline de Ordem de Serviço");
            return "relatorios/timeline-os";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao gerar relatório: " + e.getMessage());
            return "relatorios/timeline-os";
        }
    }

    @GetMapping("/consumo-equipe")
    public String consumoEquipe(
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim,
            Model model) {
        try {
            if (dataInicio != null && dataFim != null) {
                model.addAttribute("registros", relatoriosDAO.consumoPorEquipe(dataInicio, dataFim));
                model.addAttribute("dataInicio", dataInicio);
                model.addAttribute("dataFim", dataFim);
            }
            model.addAttribute("titulo", "Consumo de Materiais por Equipe");
            return "relatorios/consumo-equipe";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao gerar relatório: " + e.getMessage());
            return "relatorios/consumo-equipe";
        }
    }

    @GetMapping("/os-concluidas-ano")
    public String osConcluidas(@RequestParam(required = false) Integer ano, Model model) {
        try {
            if (ano == null) {
                ano = java.time.Year.now().getValue();
            }
            model.addAttribute("registros", relatoriosDAO.osConcluidasPorTipo(ano));
            model.addAttribute("ano", ano);
            model.addAttribute("titulo", "OS Concluídas por Tipo no Ano");
            return "relatorios/os-concluidas-ano";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao gerar relatório: " + e.getMessage());
            return "relatorios/os-concluidas-ano";
        }
    }
}



