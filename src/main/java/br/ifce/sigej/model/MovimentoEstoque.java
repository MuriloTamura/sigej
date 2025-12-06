package br.ifce.sigej.model;

import java.time.LocalDateTime;

public class MovimentoEstoque {

    private int id;
    private int produtoVariacaoId;
    private int localEstoqueId;
    private int tipoMovimentoId;
    private double quantidade;
    private LocalDateTime dataHora;
    private Integer funcionarioId;
    private Integer ordemServicoId;
    private String observacao;

    public MovimentoEstoque() {}

    public MovimentoEstoque(int produtoVariacaoId, int localEstoqueId, int tipoMovimentoId,
                            double quantidade, Integer funcionarioId, Integer ordemServicoId, String observacao) {

        this.produtoVariacaoId = produtoVariacaoId;
        this.localEstoqueId = localEstoqueId;
        this.tipoMovimentoId = tipoMovimentoId;
        this.quantidade = quantidade;
        this.funcionarioId = funcionarioId;
        this.ordemServicoId = ordemServicoId;
        this.observacao = observacao;
    }

    public MovimentoEstoque(int id, int produtoVariacaoId, int localEstoqueId, int tipoMovimentoId,
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

    public int getProdutoVariacaoId() { return produtoVariacaoId; }
    public void setProdutoVariacaoId(int produtoVariacaoId) { this.produtoVariacaoId = produtoVariacaoId; }

    public int getLocalEstoqueId() { return localEstoqueId; }
    public void setLocalEstoqueId(int localEstoqueId) { this.localEstoqueId = localEstoqueId; }

    public int getTipoMovimentoId() { return tipoMovimentoId; }
    public void setTipoMovimentoId(int tipoMovimentoId) { this.tipoMovimentoId = tipoMovimentoId; }

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
