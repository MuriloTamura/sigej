package br.ifce.sigej.model;

import java.time.LocalDate;

public class Funcionario {

    private Integer id;
    private Integer pessoaId;
    private Integer tipoFuncionarioId;
    private Integer setorId;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;

    public Funcionario() {}

    public Funcionario(Integer pessoaId, Integer tipoFuncionarioId, Integer setorId,
                       LocalDate dataAdmissao, LocalDate dataDemissao) {
        this.pessoaId = pessoaId;
        this.tipoFuncionarioId = tipoFuncionarioId;
        this.setorId = setorId;
        this.dataAdmissao = dataAdmissao;
        this.dataDemissao = dataDemissao;
    }

    public Funcionario(Integer id, Integer pessoaId, Integer tipoFuncionarioId, Integer setorId,
                       LocalDate dataAdmissao, LocalDate dataDemissao) {
        this.id = id;
        this.pessoaId = pessoaId;
        this.tipoFuncionarioId = tipoFuncionarioId;
        this.setorId = setorId;
        this.dataAdmissao = dataAdmissao;
        this.dataDemissao = dataDemissao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Integer pessoaId) {
        this.pessoaId = pessoaId;
    }

    public Integer getTipoFuncionarioId() {
        return tipoFuncionarioId;
    }

    public void setTipoFuncionarioId(Integer tipoFuncionarioId) {
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
    }}
