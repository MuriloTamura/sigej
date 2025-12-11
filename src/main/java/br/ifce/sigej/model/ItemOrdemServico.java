package br.ifce.sigej.model;

public class ItemOrdemServico {

    private int id;
    private Integer osId;
    private Integer produtoVariacaoId;
    private double quantidadePrevista;
    private double quantidadeUsada;

    // Objetos relacionados para exibição
    private OrdemServico ordemServico;
    private ProdutoVariacao produtoVariacao;

    public ItemOrdemServico() {}

    public ItemOrdemServico(Integer osId, Integer produtoVariacaoId, double quantidadePrevista, double quantidadeUsada) {
        this.osId = osId;
        this.produtoVariacaoId = produtoVariacaoId;
        this.quantidadePrevista = quantidadePrevista;
        this.quantidadeUsada = quantidadeUsada;
    }

    public ItemOrdemServico(int id, Integer osId, Integer produtoVariacaoId, double quantidadePrevista, double quantidadeUsada) {
        this.id = id;
        this.osId = osId;
        this.produtoVariacaoId = produtoVariacaoId;
        this.quantidadePrevista = quantidadePrevista;
        this.quantidadeUsada = quantidadeUsada;
    }

    // GETTERS E SETTERS

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getOsId() { return osId; }
    public void setOsId(Integer osId) { this.osId = osId; }

    public Integer getProdutoVariacaoId() { return produtoVariacaoId; }
    public void setProdutoVariacaoId(Integer produtoVariacaoId) { this.produtoVariacaoId = produtoVariacaoId; }

    public double getQuantidadePrevista() { return quantidadePrevista; }
    public void setQuantidadePrevista(double quantidadePrevista) { this.quantidadePrevista = quantidadePrevista; }

    public double getQuantidadeUsada() { return quantidadeUsada; }
    public void setQuantidadeUsada(double quantidadeUsada) { this.quantidadeUsada = quantidadeUsada; }

    // Getters e Setters dos objetos relacionados

    public OrdemServico getOrdemServico() { return ordemServico; }
    public void setOrdemServico(OrdemServico ordemServico) { this.ordemServico = ordemServico; }

    public ProdutoVariacao getProdutoVariacao() { return produtoVariacao; }
    public void setProdutoVariacao(ProdutoVariacao produtoVariacao) { this.produtoVariacao = produtoVariacao; }
}