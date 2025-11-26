package br.ifce.sigej.model;

public class TipoMovimentoEstoque {

    private int id;
    private String descricao;
    private String sinal; // '+' ou '-'

    public TipoMovimentoEstoque() {}

    public TipoMovimentoEstoque(String descricao, String sinal) {
        this.descricao = descricao;
        this.sinal = sinal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getSinal() { return sinal; }
    public void setSinal(String sinal) { this.sinal = sinal; }
}
