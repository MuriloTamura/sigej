package br.ifce.sigej.model;

public class UnidadeMedida {

    private int id;
    private String sigla;
    private String descricao;

    public UnidadeMedida() {}

    public UnidadeMedida(String sigla, String descricao) {
        this.sigla = sigla;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
