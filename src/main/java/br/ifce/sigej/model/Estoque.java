package br.ifce.sigej.model;

public class Estoque {

    private Integer produtoVariacaoId;
    private Integer localEstoqueId;
    private Double quantidade;
    private Double pontoReposicao;

    public Estoque() {}

    public Estoque(Integer produtoVariacaoId, Integer localEstoqueId, Double quantidade, Double pontoReposicao) {
        this.produtoVariacaoId = produtoVariacaoId;
        this.localEstoqueId = localEstoqueId;
        this.quantidade = quantidade;
        this.pontoReposicao = pontoReposicao;
    }

    public Integer getProdutoVariacaoId() {
        return produtoVariacaoId;
    }

    public void setProdutoVariacaoId(Integer produtoVariacaoId) {
        this.produtoVariacaoId = produtoVariacaoId;
    }

    public Integer getLocalEstoqueId() {
        return localEstoqueId;
    }

    public void setLocalEstoqueId(Integer localEstoqueId) {
        this.localEstoqueId = localEstoqueId;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPontoReposicao() {
        return pontoReposicao;
    }

    public void setPontoReposicao(Double pontoReposicao) {
        this.pontoReposicao = pontoReposicao;
    }
}
