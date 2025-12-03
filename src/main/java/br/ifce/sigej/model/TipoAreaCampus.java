package br.ifce.sigej.model;

public class TipoAreaCampus {

    private int id;
    private String descricao;

    public TipoAreaCampus() {}

    public TipoAreaCampus(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
