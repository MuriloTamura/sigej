package br.ifce.sigej.model;

public class TipoFuncionario {

    private int id;
    private String nome;

    public TipoFuncionario() {}

    public TipoFuncionario(String nome) {
        this.nome = nome;
    }

    // GETTERS E SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
