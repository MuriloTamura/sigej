package br.ifce.sigej.model;

public class LocalEstoque {

    private int id;
    private String descricao;
    private Integer responsavelId;

    public LocalEstoque() {}

    public LocalEstoque(String descricao, Integer responsavelId) {
        this.descricao = descricao;
        this.responsavelId = responsavelId;
    }

    public LocalEstoque(int id, String descricao, Integer responsavelId) {
        this.id = id;
        this.descricao = descricao;
        this.responsavelId = responsavelId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getResponsavelId() { return responsavelId; }
    public void setResponsavelId(Integer responsavelId) { this.responsavelId = responsavelId; }
}
