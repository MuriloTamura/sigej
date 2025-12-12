package br.ifce.sigej.model;

public class AreaCampus {

    private int id;
    private Integer tipoAreaId;
    private String descricao;
    private String bloco;

    // Campo extra para exibição (não existe no banco)
    private String tipoAreaDescricao;

    public AreaCampus() {}

    public AreaCampus(Integer tipoAreaId, String descricao, String bloco) {
        this.tipoAreaId = tipoAreaId;
        this.descricao = descricao;
        this.bloco = bloco;
    }

    public AreaCampus(int id, Integer tipoAreaId, String descricao, String bloco) {
        this.id = id;
        this.tipoAreaId = tipoAreaId;
        this.descricao = descricao;
        this.bloco = bloco;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getTipoAreaId() { return tipoAreaId; }
    public void setTipoAreaId(Integer tipoAreaId) { this.tipoAreaId = tipoAreaId; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getBloco() { return bloco; }
    public void setBloco(String bloco) { this.bloco = bloco; }

    // Getter e Setter do campo extra
    public String getTipoAreaDescricao() { return tipoAreaDescricao; }
    public void setTipoAreaDescricao(String tipoAreaDescricao) { this.tipoAreaDescricao = tipoAreaDescricao; }
}