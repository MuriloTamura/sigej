package br.ifce.sigej.controller;

import br.ifce.sigej.dao.ProdutoDAO;
import br.ifce.sigej.dao.CategoriaMaterialDAO;
import br.ifce.sigej.dao.UnidadeMedidaDAO;
import br.ifce.sigej.dao.MarcaDAO;
import br.ifce.sigej.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoDAO produtoDAO;

    @Autowired
    private CategoriaMaterialDAO categoriaMaterialDAO;

    @Autowired
    private UnidadeMedidaDAO unidadeMedidaDAO;

    @Autowired
    private MarcaDAO marcaDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", produtoDAO.listar());
        model.addAttribute("categorias", categoriaMaterialDAO.listar());
        model.addAttribute("unidades", unidadeMedidaDAO.listar());
        model.addAttribute("marcas", marcaDAO.listar());
        return "produto/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaMaterialDAO.listar());
        model.addAttribute("unidades", unidadeMedidaDAO.listar());
        model.addAttribute("marcas", marcaDAO.listar());
        return "produto/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        return produtoDAO.buscarPorId(id)
                .map(produto -> {
                    model.addAttribute("produto", produto);
                    model.addAttribute("categorias", categoriaMaterialDAO.listar());
                    model.addAttribute("unidades", unidadeMedidaDAO.listar());
                    model.addAttribute("marcas", marcaDAO.listar());
                    return "produto/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Produto não encontrado!");
                    return "redirect:/produtos";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto, RedirectAttributes redirectAttributes) {
        try {
            if (produto.getId() == 0) {
                produtoDAO.inserir(produto);
                redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
            } else {
                produtoDAO.atualizar(produto);
                redirectAttributes.addFlashAttribute("sucesso", "Produto atualizado com sucesso!");
            }
            return "redirect:/produtos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "redirect:/produtos/novo";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            produtoDAO.deletar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Produto excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/produtos";
    }
}