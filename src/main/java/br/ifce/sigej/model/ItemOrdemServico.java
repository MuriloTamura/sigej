package br.ifce.sigej.model;

public class ItemOrdemServico {

    private int id;
    private Integer osId;
    private Integer produtoVariacaoId;
    private Double quantidadePrevista;
    private Double quantidadeUsada;

    public ItemOrdemServico() {}

    public ItemOrdemServico(Integer osId, Integer produtoVariacaoId,
                            Double quantidadePrevista, Double quantidadeUsada) {
        this.osId = osId;
        this.produtoVariacaoId = produtoVariacaoId;
        this.quantidadePrevista = quantidadePrevista;
        this.quantidadeUsada = quantidadeUsada;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getOsId() { return osId; }
    public void setOsId(Integer osId) { this.osId = osId; }

    public Integer getProdutoVariacaoId() { return produtoVariacaoId; }
    public void setProdutoVariacaoId(Integer produtoVariacaoId) { this.produtoVariacaoId = produtoVariacaoId; }

    public Double getQuantidadePrevista() { return quantidadePrevista; }
    public void setQuantidadePrevista(Double quantidadePrevista) { this.quantidadePrevista = quantidadePrevista; }

    public Double getQuantidadeUsada() { return quantidadeUsada; }
    public void setQuantidadeUsada(Double quantidadeUsada) { this.quantidadeUsada = quantidadeUsada; }
}
