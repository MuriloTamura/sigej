package br.ifce.sigej.model;

import java.time.LocalDate;

public class EquipeMembro {

    private int id;
    private Integer equipeId;      // Integer ao invés de int
    private Integer funcionarioId;  // Integer ao invés de int
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String funcao;

    public EquipeMembro() {}

    public EquipeMembro(Integer equipeId, Integer funcionarioId, LocalDate dataInicio, LocalDate dataFim, String funcao) {
        this.equipeId = equipeId;
        this.funcionarioId = funcionarioId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.funcao = funcao;
    }

    public EquipeMembro(int id, Integer equipeId, Integer funcionarioId, LocalDate dataInicio, LocalDate dataFim, String funcao) {
        this.id = id;
        this.equipeId = equipeId;
        this.funcionarioId = funcionarioId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.funcao = funcao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getEquipeId() { return equipeId; }
    public void setEquipeId(Integer equipeId) { this.equipeId = equipeId; }

    public Integer getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Integer funcionarioId) { this.funcionarioId = funcionarioId; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }
}