package br.ifce.sigej.model;

public class Estoque {
    private int produtoVariacaoId;
    private int localEstoqueId;
    private double quantidade;
    private double pontoReposicao;

    // Campos extras para exibição (não são salvos no banco)
    private String produtoDescricao;
    private String localDescricao;
    private String codigoInterno;

    public Estoque() {}

    public Estoque(int produtoVariacaoId, int localEstoqueId, double quantidade, double pontoReposicao) {
        this.produtoVariacaoId = produtoVariacaoId;
        this.localEstoqueId = localEstoqueId;
        this.quantidade = quantidade;
        this.pontoReposicao = pontoReposicao;
    }

    public int getProdutoVariacaoId() { return produtoVariacaoId; }
    public void setProdutoVariacaoId(int produtoVariacaoId) { this.produtoVariacaoId = produtoVariacaoId; }

    public int getLocalEstoqueId() { return localEstoqueId; }
    public void setLocalEstoqueId(int localEstoqueId) { this.localEstoqueId = localEstoqueId; }

    public double getQuantidade() { return quantidade; }
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }

    public double getPontoReposicao() { return pontoReposicao; }
    public void setPontoReposicao(double pontoReposicao) { this.pontoReposicao = pontoReposicao; }

    public String getProdutoDescricao() { return produtoDescricao; }
    public void setProdutoDescricao(String produtoDescricao) { this.produtoDescricao = produtoDescricao; }

    public String getLocalDescricao() { return localDescricao; }
    public void setLocalDescricao(String localDescricao) { this.localDescricao = localDescricao; }

    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }
}