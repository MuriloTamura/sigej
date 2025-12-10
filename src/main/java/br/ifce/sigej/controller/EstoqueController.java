package br.ifce.sigej.controller;

import br.ifce.sigej.dao.EstoqueDAO;
import br.ifce.sigej.dao.LocalEstoqueDAO;
import br.ifce.sigej.dao.ProdutoVariacaoDAO;
import br.ifce.sigej.model.Estoque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/estoques")
public class EstoqueController {

    @Autowired
    private EstoqueDAO estoqueDAO;

    @Autowired
    private ProdutoVariacaoDAO produtoVariacaoDAO;

    @Autowired
    private LocalEstoqueDAO localEstoqueDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estoques", estoqueDAO.listar());
        return "estoque/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("estoque", new Estoque());
        model.addAttribute("produtosVariacao", produtoVariacaoDAO.listar());
        model.addAttribute("locaisEstoque", localEstoqueDAO.listar());
        return "estoque/form";
    }

    @GetMapping("/editar/{produtoVariacaoId}/{localEstoqueId}")
    public String editar(@PathVariable int produtoVariacaoId,
                         @PathVariable int localEstoqueId,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        return estoqueDAO.buscarPorId(produtoVariacaoId, localEstoqueId)
                .map(estoque -> {
                    model.addAttribute("estoque", estoque);
                    model.addAttribute("produtosVariacao", produtoVariacaoDAO.listar());
                    model.addAttribute("locaisEstoque", localEstoqueDAO.listar());
                    return "estoque/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Item de estoque não encontrado!");
                    return "redirect:/estoques";
                });
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Estoque estoque,
                         @RequestParam(required = false) Integer produtoVariacaoIdOriginal,
                         @RequestParam(required = false) Integer localEstoqueIdOriginal,
                         RedirectAttributes redirectAttributes) {

        System.out.println("\n========================================");
        System.out.println("=== MÉTODO SALVAR CHAMADO ===");
        System.out.println("========================================");
        System.out.println("Objeto Estoque recebido:");
        System.out.println("  - ProdutoVariacaoId: " + estoque.getProdutoVariacaoId());
        System.out.println("  - LocalEstoqueId: " + estoque.getLocalEstoqueId());
        System.out.println("  - Quantidade: " + estoque.getQuantidade());
        System.out.println("  - PontoReposicao: " + estoque.getPontoReposicao());
        System.out.println("\nParâmetros Originais:");
        System.out.println("  - produtoVariacaoIdOriginal: " + produtoVariacaoIdOriginal);
        System.out.println("  - localEstoqueIdOriginal: " + localEstoqueIdOriginal);

        // Validação básica
        if (estoque.getProdutoVariacaoId() <= 0) {
            System.err.println("ERRO: ProdutoVariacaoId inválido!");
            redirectAttributes.addFlashAttribute("erro", "Produto não selecionado!");
            return "redirect:/estoques/novo";
        }

        if (estoque.getLocalEstoqueId() <= 0) {
            System.err.println("ERRO: LocalEstoqueId inválido!");
            redirectAttributes.addFlashAttribute("erro", "Local não selecionado!");
            return "redirect:/estoques/novo";
        }

        try {
            // Verificar se é edição (IDs originais maiores que 0)
            boolean isEdicao = (produtoVariacaoIdOriginal != null && localEstoqueIdOriginal != null
                    && produtoVariacaoIdOriginal > 0 && localEstoqueIdOriginal > 0);

            System.out.println("\nOperação identificada: " + (isEdicao ? "ATUALIZAÇÃO" : "INSERÇÃO"));

            if (isEdicao) {
                // É uma edição - usar os IDs originais para atualizar
                estoque.setProdutoVariacaoId(produtoVariacaoIdOriginal);
                estoque.setLocalEstoqueId(localEstoqueIdOriginal);

                System.out.println("Chamando estoqueDAO.atualizar()...");
                estoqueDAO.atualizar(estoque);
                System.out.println("Atualização concluída!");
                redirectAttributes.addFlashAttribute("sucesso", "Item de estoque atualizado com sucesso!");
            } else {
                // É uma nova inserção - verificar se já existe
                Optional<Estoque> estoqueExistente = estoqueDAO.buscarPorId(
                        estoque.getProdutoVariacaoId(),
                        estoque.getLocalEstoqueId()
                );

                if (estoqueExistente.isPresent()) {
                    System.err.println("ERRO: Item já existe no estoque!");
                    redirectAttributes.addFlashAttribute("erro",
                            "Este produto já está cadastrado neste local de estoque!");
                    return "redirect:/estoques/novo";
                }

                System.out.println("Chamando estoqueDAO.inserir()...");
                estoqueDAO.inserir(estoque);
                System.out.println("Inserção concluída!");
                redirectAttributes.addFlashAttribute("sucesso", "Item de estoque cadastrado com sucesso!");
            }

            System.out.println("Redirecionando para /estoques");
            System.out.println("========================================\n");
            return "redirect:/estoques";

        } catch (Exception e) {
            System.err.println("\n========================================");
            System.err.println("=== ERRO NO MÉTODO SALVAR ===");
            System.err.println("========================================");
            System.err.println("Tipo de exceção: " + e.getClass().getName());
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Stack trace:");
            e.printStackTrace();
            System.err.println("========================================\n");

            // Mensagem mais amigável para o usuário
            String mensagemErro = e.getMessage();
            if (mensagemErro.contains("duplicate key")) {
                mensagemErro = "Este produto já está cadastrado neste local de estoque!";
            }

            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar: " + mensagemErro);
            return "redirect:/estoques/novo";
        }
    }

    @GetMapping("/deletar/{produtoVariacaoId}/{localEstoqueId}")
    public String deletar(@PathVariable int produtoVariacaoId,
                          @PathVariable int localEstoqueId,
                          RedirectAttributes redirectAttributes) {
        try {
            estoqueDAO.deletar(produtoVariacaoId, localEstoqueId);
            redirectAttributes.addFlashAttribute("sucesso", "Item de estoque excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/estoques";
    }
}