package br.ifce.sigej.model;

import java.time.LocalDateTime;

public class AndamentoOrdemServico {

    private int id;
    private int osId;
    private LocalDateTime dataHora;
    private Integer statusAnteriorId;
    private Integer statusNovoId;
    private Integer funcionarioId;
    private String descricao;
    private LocalDateTime inicioAtendimento;
    private LocalDateTime fimAtendimento;

    public AndamentoOrdemServico() {}

    public AndamentoOrdemServico(int osId, Integer statusAnteriorId, Integer statusNovoId,
                                 Integer funcionarioId, String descricao,
                                 LocalDateTime inicioAtendimento, LocalDateTime fimAtendimento) {

        this.osId = osId;
        this.statusAnteriorId = statusAnteriorId;
        this.statusNovoId = statusNovoId;
        this.funcionarioId = funcionarioId;
        this.descricao = descricao;
        this.inicioAtendimento = inicioAtendimento;
        this.fimAtendimento = fimAtendimento;
    }

    public AndamentoOrdemServico(int id, int osId, LocalDateTime dataHora,
                                 Integer statusAnteriorId, Integer statusNovoId,
                                 Integer funcionarioId, String descricao,
                                 LocalDateTime inicioAtendimento, LocalDateTime fimAtendimento) {

        this.id = id;
        this.osId = osId;
        this.dataHora = dataHora;
        this.statusAnteriorId = statusAnteriorId;
        this.statusNovoId = statusNovoId;
        this.funcionarioId = funcionarioId;
        this.descricao = descricao;
        this.inicioAtendimento = inicioAtendimento;
        this.fimAtendimento = fimAtendimento;
    }

    // GETTERS E SETTERS

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOsId() { return osId; }
    public void setOsId(int osId) { this.osId = osId; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Integer getStatusAnteriorId() { return statusAnteriorId; }
    public void setStatusAnteriorId(Integer statusAnteriorId) { this.statusAnteriorId = statusAnteriorId; }

    public Integer getStatusNovoId() { return statusNovoId; }
    public void setStatusNovoId(Integer statusNovoId) { this.statusNovoId = statusNovoId; }

    public Integer getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Integer funcionarioId) { this.funcionarioId = funcionarioId; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getInicioAtendimento() { return inicioAtendimento; }
    public void setInicioAtendimento(LocalDateTime inicioAtendimento) { this.inicioAtendimento = inicioAtendimento; }

    public LocalDateTime getFimAtendimento() { return fimAtendimento; }
    public void setFimAtendimento(LocalDateTime fimAtendimento) { this.fimAtendimento = fimAtendimento; }
}
