package br.ifce.sigej.model;

import java.time.LocalDateTime;

public class AndamentoOrdemServico {

    private int id;
    private Integer osId;
    private LocalDateTime dataHora;
    private Integer statusAnteriorId;
    private Integer statusNovoId;
    private Integer funcionarioId;
    private String descricao;
    private LocalDateTime inicioAtendimento;
    private LocalDateTime fimAtendimento;

    // Atributos adicionais para exibição (não persistidos)
    private String osNumeroSequencial;
    private String statusAnteriorDescricao;
    private String statusNovoDescricao;
    private String funcionarioNome;

    public AndamentoOrdemServico() {}

    public AndamentoOrdemServico(Integer osId, Integer statusAnteriorId, Integer statusNovoId,
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

    public AndamentoOrdemServico(int id, Integer osId, LocalDateTime dataHora,
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

    public Integer getOsId() { return osId; }
    public void setOsId(Integer osId) { this.osId = osId; }

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

    // Getters e Setters dos atributos de exibição

    public String getOsNumeroSequencial() { return osNumeroSequencial; }
    public void setOsNumeroSequencial(String osNumeroSequencial) { this.osNumeroSequencial = osNumeroSequencial; }

    public String getStatusAnteriorDescricao() { return statusAnteriorDescricao; }
    public void setStatusAnteriorDescricao(String statusAnteriorDescricao) { this.statusAnteriorDescricao = statusAnteriorDescricao; }

    public String getStatusNovoDescricao() { return statusNovoDescricao; }
    public void setStatusNovoDescricao(String statusNovoDescricao) { this.statusNovoDescricao = statusNovoDescricao; }

    public String getFuncionarioNome() { return funcionarioNome; }
    public void setFuncionarioNome(String funcionarioNome) { this.funcionarioNome = funcionarioNome; }
}