package br.ifce.sigej.model;

public class TipoMovimentoEstoque {

    private int id;
    private String descricao;
    private char sinal;

    public TipoMovimentoEstoque() {}

    public TipoMovimentoEstoque(String descricao, char sinal) {
        this.descricao = descricao;
        this.sinal = sinal;
    }

    public TipoMovimentoEstoque(int id, String descricao, char sinal) {
        this.id = id;
        this.descricao = descricao;
        this.sinal = sinal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public char getSinal() { return sinal; }
    public void setSinal(char sinal) { this.sinal = sinal; }
}
