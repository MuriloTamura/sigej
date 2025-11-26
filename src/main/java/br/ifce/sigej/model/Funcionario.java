package br.ifce.sigej.model;

import java.time.LocalDate;

public class Funcionario {

    private int id;
    private int pessoaId;
    private int tipoFuncionarioId;
    private int setorId;

    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;

    public Funcionario() {}

    public Funcionario(int pessoaId, int tipoFuncionarioId, int setorId, LocalDate dataAdmissao) {
        this.pessoaId = pessoaId;
        this.tipoFuncionarioId = tipoFuncionarioId;
        this.setorId = setorId;
        this.dataAdmissao = dataAdmissao;
    }

    // GETTERS E SETTERS

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getPessoaId() { return pessoaId; }

    public void setPessoaId(int pessoaId) { this.pessoaId = pessoaId; }

    public int getTipoFuncionarioId() { return tipoFuncionarioId; }

    public void setTipoFuncionarioId(int tipoFuncionarioId) { this.tipoFuncionarioId = tipoFuncionarioId; }

    public int getSetorId() { return setorId; }

    public void setSetorId(int setorId) { this.setorId = setorId; }

    public LocalDate getDataAdmissao() { return dataAdmissao; }

    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }

    public LocalDate getDataDemissao() { return dataDemissao; }

    public void setDataDemissao(LocalDate dataDemissao) { this.dataDemissao = dataDemissao; }
}
