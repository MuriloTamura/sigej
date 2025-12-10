package br.ifce.sigej.controller;

import br.ifce.sigej.dao.ProdutoVariacaoDAO;
import br.ifce.sigej.dao.ProdutoDAO;
import br.ifce.sigej.dao.CorDAO;
import br.ifce.sigej.dao.TamanhoDAO;
import br.ifce.sigej.model.ProdutoVariacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/produto-variacoes")
public class ProdutoVariacaoController {

    @Autowired
    private ProdutoVariacaoDAO produtoVariacaoDAO;

    @Autowired
    private ProdutoDAO produtoDAO;

    @Autowired
    private CorDAO corDAO;

    @Autowired
    private TamanhoDAO tamanhoDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("variacoes", produtoVariacaoDAO.listar());
        return "produtovariacao/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("variacao", new ProdutoVariacao());
        model.addAttribute("produtos", produtoDAO.listar());
        model.addAttribute("cores", corDAO.listar());
        model.addAttribute("tamanhos", tamanhoDAO.listar());
        return "produtovariacao/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return produtoVariacaoDAO.buscarPorId(id)
                .map(variacao -> {
                    model.addAttribute("variacao", variacao);
                    model.addAttribute("produtos", produtoDAO.listar());
                    model.addAttribute("cores", corDAO.listar());
                    model.addAttribute("tamanhos", tamanhoDAO.listar());
                    return "produtovariacao/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Variação não encontrada!");
                    return "redirect:/produto-variacoes";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute ProdutoVariacao variacao, RedirectAttributes redirectAttributes) {
        try {
            if (variacao.getId() == 0) {
                produtoVariacaoDAO.inserir(variacao);
                redirectAttributes.addFlashAttribute("sucesso", "Variação cadastrada com sucesso!");
            } else {
                produtoVariacaoDAO.atualizar(variacao);
                redirectAttributes.addFlashAttribute("sucesso", "Variação atualizada com sucesso!");
            }
            return "redirect:/produto-variacoes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/produto-variacoes/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            produtoVariacaoDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Variação excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/produto-variacoes";
    }
}