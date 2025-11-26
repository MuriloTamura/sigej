package br.ifce.sigej.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimentoEstoque {

    private int id;
    private Integer produtoVariacaoId;
    private Integer localEstoqueId;
    private Integer tipoMovimentoId;
    private BigDecimal quantidade;
    private LocalDateTime dataHora;
    private Integer funcionarioId;
    private Integer ordemServicoId;
    private String observacao;

    public MovimentoEstoque() {}

    public MovimentoEstoque(Integer produtoVariacaoId, Integer localEstoqueId, Integer tipoMovimentoId,
                            BigDecimal quantidade, Integer funcionarioId, Integer ordemServicoId, String observacao) {

        this.produtoVariacaoId = produtoVariacaoId;
        this.localEstoqueId = localEstoqueId;
        this.tipoMovimentoId = tipoMovimentoId;
        this.quantidade = quantidade;
        this.funcionarioId = funcionarioId;
        this.ordemServicoId = ordemServicoId;
        this.observacao = observacao;
    }

    // Getters e Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getProdutoVariacaoId() { return produtoVariacaoId; }
    public void setProdutoVariacaoId(Integer produtoVariacaoId) { this.produtoVariacaoId = produtoVariacaoId; }

    public Integer getLocalEstoqueId() { return localEstoqueId; }
    public void setLocalEstoqueId(Integer localEstoqueId) { this.localEstoqueId = localEstoqueId; }

    public Integer getTipoMovimentoId() { return tipoMovimentoId; }
    public void setTipoMovimentoId(Integer tipoMovimentoId) { this.tipoMovimentoId = tipoMovimentoId; }

    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Integer getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Integer funcionarioId) { this.funcionarioId = funcionarioId; }

    public Integer getOrdemServicoId() { return ordemServicoId; }
    public void setOrdemServicoId(Integer ordemServicoId) { this.ordemServicoId = ordemServicoId; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
