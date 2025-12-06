package br.ifce.sigej.model;

import java.time.LocalDate;

public class Funcionario {

    private int id;
    private int pessoaId;
    private int tipoFuncionarioId;
    private Integer setorId;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;

    public Funcionario() {}

    public Funcionario(int pessoaId, int tipoFuncionarioId, Integer setorId,
                       LocalDate dataAdmissao, LocalDate dataDemissao) {
        this.pessoaId = pessoaId;
        this.tipoFuncionarioId = tipoFuncionarioId;
        this.setorId = setorId;
        this.dataAdmissao = dataAdmissao;
        this.dataDemissao = dataDemissao;
    }

    public Funcionario(int id, int pessoaId, int tipoFuncionarioId, Integer setorId,
                       LocalDate dataAdmissao, LocalDate dataDemissao) {
        this.id = id;
        this.pessoaId = pessoaId;
        this.tipoFuncionarioId = tipoFuncionarioId;
        this.setorId = setorId;
        this.dataAdmissao = dataAdmissao;
        this.dataDemissao = dataDemissao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(int pessoaId) {
        this.pessoaId = pessoaId;
    }

    public int getTipoFuncionarioId() {
        return tipoFuncionarioId;
    }

    public void setTipoFuncionarioId(int tipoFuncionarioId) {
        this.tipoFuncionarioId = tipoFuncionarioId;
    }

    public Integer getSetorId() {
        return setorId;
    }

    public void setSetorId(Integer setorId) {
        this.setorId = setorId;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }
}
