package br.ifce.sigej.model;

public class ProdutoVariacao {

    private int id;
    private Integer produtoId;
    private Integer corId;
    private Integer tamanhoId;
    private String codigoBarras;
    private String codigoInterno;

    public ProdutoVariacao() {}

    public ProdutoVariacao(Integer produtoId, Integer corId, Integer tamanhoId, String codigoBarras, String codigoInterno) {
        this.produtoId = produtoId;
        this.corId = corId;
        this.tamanhoId = tamanhoId;
        this.codigoBarras = codigoBarras;
        this.codigoInterno = codigoInterno;
    }

    public ProdutoVariacao(int id, Integer produtoId, Integer corId, Integer tamanhoId, String codigoBarras, String codigoInterno) {
        this.id = id;
        this.produtoId = produtoId;
        this.corId = corId;
        this.tamanhoId = tamanhoId;
        this.codigoBarras = codigoBarras;
        this.codigoInterno = codigoInterno;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getProdutoId() { return produtoId; }
    public void setProdutoId(Integer produtoId) { this.produtoId = produtoId; }

    public Integer getCorId() { return corId; }
    public void setCorId(Integer corId) { this.corId = corId; }

    public Integer getTamanhoId() { return tamanhoId; }
    public void setTamanhoId(Integer tamanhoId) { this.tamanhoId = tamanhoId; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }
}
