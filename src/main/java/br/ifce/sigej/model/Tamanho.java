package br.ifce.sigej.model;

public class Tamanho {

    private int id;
    private String descricao;

    public Tamanho() {}

    public Tamanho(String descricao) {
        this.descricao = descricao;
    }

    public Tamanho(int id, String descricao) {
        this.id = id;
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
