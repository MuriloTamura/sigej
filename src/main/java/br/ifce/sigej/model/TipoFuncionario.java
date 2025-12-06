package br.ifce.sigej.model;

public class TipoFuncionario {

    private int id;
    private String descricao;

    public TipoFuncionario() {}

    public TipoFuncionario(String descricao) {
        this.descricao = descricao;
    }

    public TipoFuncionario(int id, String descricao) {
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
