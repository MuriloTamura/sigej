package br.ifce.sigej.model;

import java.time.LocalDateTime;

public class MovimentoEstoque {

    private int id;
    private Integer produtoVariacaoId;
    private Integer localEstoqueId;
    private Integer tipoMovimentoId;
    private double quantidade;
    private LocalDateTime dataHora;
    private Integer funcionarioId;
    private Integer ordemServicoId;
    private String observacao;

    // Objetos relacionados para exibição
    private ProdutoVariacao produtoVariacao;
    private LocalEstoque localEstoque;
    private TipoMovimentoEstoque tipoMovimento;
    private Funcionario funcionario;

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

    // Getters e Setters dos objetos relacionados

    public ProdutoVariacao getProdutoVariacao() { return produtoVariacao; }
    public void setProdutoVariacao(ProdutoVariacao produtoVariacao) { this.produtoVariacao = produtoVariacao; }

    public LocalEstoque getLocalEstoque() { return localEstoque; }
    public void setLocalEstoque(LocalEstoque localEstoque) { this.localEstoque = localEstoque; }

    public TipoMovimentoEstoque getTipoMovimento() { return tipoMovimento; }
    public void setTipoMovimento(TipoMovimentoEstoque tipoMovimento) { this.tipoMovimento = tipoMovimento; }

    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }
}