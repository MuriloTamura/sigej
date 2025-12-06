package br.ifce.sigej.model;

public class AreaCampus {

    private int id;
    private int tipoAreaId;
    private String descricao;
    private String bloco;

    public AreaCampus() {}

    public AreaCampus(int tipoAreaId, String descricao, String bloco) {
        this.tipoAreaId = tipoAreaId;
        this.descricao = descricao;
        this.bloco = bloco;
    }

    public AreaCampus(int id, int tipoAreaId, String descricao, String bloco) {
        this.id = id;
        this.tipoAreaId = tipoAreaId;
        this.descricao = descricao;
        this.bloco = bloco;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTipoAreaId() { return tipoAreaId; }
    public void setTipoAreaId(int tipoAreaId) { this.tipoAreaId = tipoAreaId; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getBloco() { return bloco; }
    public void setBloco(String bloco) { this.bloco = bloco; }
}
