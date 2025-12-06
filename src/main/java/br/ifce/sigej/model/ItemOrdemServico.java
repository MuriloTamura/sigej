package br.ifce.sigej.model;

public class ItemOrdemServico {

    private int id;
    private int osId;
    private int produtoVariacaoId;
    private double quantidadePrevista;
    private double quantidadeUsada;

    public ItemOrdemServico() {}

    public ItemOrdemServico(int osId, int produtoVariacaoId, double quantidadePrevista, double quantidadeUsada) {
        this.osId = osId;
        this.produtoVariacaoId = produtoVariacaoId;
        this.quantidadePrevista = quantidadePrevista;
        this.quantidadeUsada = quantidadeUsada;
    }

    public ItemOrdemServico(int id, int osId, int produtoVariacaoId, double quantidadePrevista, double quantidadeUsada) {
        this.id = id;
        this.osId = osId;
        this.produtoVariacaoId = produtoVariacaoId;
        this.quantidadePrevista = quantidadePrevista;
        this.quantidadeUsada = quantidadeUsada;
    }

    // GETTERS E SETTERS

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOsId() { return osId; }
    public void setOsId(int osId) { this.osId = osId; }

    public int getProdutoVariacaoId() { return produtoVariacaoId; }
    public void setProdutoVariacaoId(int produtoVariacaoId) { this.produtoVariacaoId = produtoVariacaoId; }

    public double getQuantidadePrevista() { return quantidadePrevista; }
    public void setQuantidadePrevista(double quantidadePrevista) { this.quantidadePrevista = quantidadePrevista; }

    public double getQuantidadeUsada() { return quantidadeUsada; }
    public void setQuantidadeUsada(double quantidadeUsada) { this.quantidadeUsada = quantidadeUsada; }
}
