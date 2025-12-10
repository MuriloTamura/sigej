package br.ifce.sigej.model;

public class TipoMovimentoEstoque {

    private int id;
    private String descricao;
    private Character sinal;  // Mudou de char para Character

    public TipoMovimentoEstoque() {}

    public TipoMovimentoEstoque(String descricao, Character sinal) {
        this.descricao = descricao;
        this.sinal = sinal;
    }

    public TipoMovimentoEstoque(int id, String descricao, Character sinal) {
        this.id = id;
        this.descricao = descricao;
        this.sinal = sinal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Character getSinal() { return sinal; }
    public void setSinal(Character sinal) { this.sinal = sinal; }
}