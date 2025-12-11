package br.ifce.sigej.model;

import java.time.LocalDateTime;

public class MovimentoEstoque {

    private int id;
    private Integer produtoVariacaoId;  // Integer para evitar problemas com Thymeleaf
    private Integer localEstoqueId;     // Integer para evitar problemas com Thymeleaf
    private Integer tipoMovimentoId;    // Integer para evitar problemas com Thymeleaf
    private double quantidade;
    private LocalDateTime dataHora;
    private Integer funcionarioId;
    private Integer ordemServicoId;
    private String observacao;

    public MovimentoEstoque() {}

    public MovimentoEstoque(Integer produtoVariacaoId, Integer localEstoqueId, Integer tipoMovimentoId,
                            double quantidade, Integer funcionarioId, Integer ordemServicoId, String observacao) {

        this.produtoVariacaoId = produtoVariacaoId;
        this.localEstoqueId = localEstoqueId;
        this.tipoMovimentoId = tipoMovimentoId;
        this.quantidade = quantidade;
        this.funcionarioId = funcionarioId;
        this.ordemServicoId = ordemServicoId;
        this.observacao = observacao;
    }

    public MovimentoEstoque(int id, Integer produtoVariacaoId, Integer localEstoqueId, Integer tipoMovimentoId,
                            double quantidade, LocalDateTime dataHora, Integer funcionarioId,
                            Integer ordemServicoId, String observacao) {

        this.id = id;
        this.produtoVariacaoId = produtoVariacaoId;
        this.localEstoqueId = localEstoqueId;
        this.tipoMovimentoId = tipoMovimentoId;
        this.quantidade = quantidade;
        this.dataHora = dataHora;
        this.funcionarioId = funcionarioId;
        this.ordemServicoId = ordemServicoId;
        this.observacao = observacao;
    }

    // GETTERS E SETTERS

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getProdutoVariacaoId() { return produtoVariacaoId; }
    public void setProdutoVariacaoId(Integer produtoVariacaoId) { this.produtoVariacaoId = produtoVariacaoId; }

    public Integer getLocalEstoqueId() { return localEstoqueId; }
    public void setLocalEstoqueId(Integer localEstoqueId) { this.localEstoqueId = localEstoqueId; }

    public Integer getTipoMovimentoId() { return tipoMovimentoId; }
    public void setTipoMovimentoId(Integer tipoMovimentoId) { this.tipoMovimentoId = tipoMovimentoId; }

    public double getQuantidade() { return quantidade; }
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Integer getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Integer funcionarioId) { this.funcionarioId = funcionarioId; }

    public Integer getOrdemServicoId() { return ordemServicoId; }
    public void setOrdemServicoId(Integer ordemServicoId) { this.ordemServicoId = ordemServicoId; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}