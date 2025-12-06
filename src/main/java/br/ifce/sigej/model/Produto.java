package br.ifce.sigej.model;

public class Produto {

    private int id;
    private String descricao;
    private Integer categoriaId;
    private Integer unidadeMedidaId;
    private Integer marcaId;

    public Produto() {}

    public Produto(String descricao, Integer categoriaId, Integer unidadeMedidaId, Integer marcaId) {
        this.descricao = descricao;
        this.categoriaId = categoriaId;
        this.unidadeMedidaId = unidadeMedidaId;
        this.marcaId = marcaId;
    }

    public Produto(int id, String descricao, Integer categoriaId, Integer unidadeMedidaId, Integer marcaId) {
        this.id = id;
        this.descricao = descricao;
        this.categoriaId = categoriaId;
        this.unidadeMedidaId = unidadeMedidaId;
        this.marcaId = marcaId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }

    public Integer getUnidadeMedidaId() { return unidadeMedidaId; }
    public void setUnidadeMedidaId(Integer unidadeMedidaId) { this.unidadeMedidaId = unidadeMedidaId; }

    public Integer getMarcaId() { return marcaId; }
    public void setMarcaId(Integer marcaId) { this.marcaId = marcaId; }
}
