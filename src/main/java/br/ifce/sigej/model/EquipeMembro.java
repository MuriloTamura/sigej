package br.ifce.sigej.model;

import java.time.LocalDate;

public class EquipeMembro {

    private int id;
    private int equipeId;
    private int funcionarioId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String funcao;

    public EquipeMembro() {}

    public EquipeMembro(int equipeId, int funcionarioId, LocalDate dataInicio, String funcao) {
        this.equipeId = equipeId;
        this.funcionarioId = funcionarioId;
        this.dataInicio = dataInicio;
        this.funcao = funcao;
    }

    // GETTERS E SETTERS
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getEquipeId() { return equipeId; }

    public void setEquipeId(int equipeId) { this.equipeId = equipeId; }

    public int getFuncionarioId() { return funcionarioId; }

    public void setFuncionarioId(int funcionarioId) { this.funcionarioId = funcionarioId; }

    public LocalDate getDataInicio() { return dataInicio; }

    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }

    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public String getFuncao() { return funcao; }

    public void setFuncao(String funcao) { this.funcao = funcao; }
}
