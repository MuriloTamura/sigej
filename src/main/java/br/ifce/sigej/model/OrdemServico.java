package br.ifce.sigej.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrdemServico {

    private int id;
    private String numeroSequencial;
    private int solicitanteId;
    private int areaCampusId;
    private int tipoOsId;
    private int equipeId;
    private int liderId;
    private int statusId;
    private int prioridade;
    private LocalDateTime dataAbertura;
    private LocalDate dataPrevista;
    private String descricaoProblema;

    public OrdemServico() {}

    public OrdemServico(String numeroSequencial, int solicitanteId, int areaCampusId,
                        int tipoOsId, int equipeId, int liderId, int statusId,
                        int prioridade, LocalDate dataPrevista, String descricaoProblema) {

        this.numeroSequencial = numeroSequencial;
        this.solicitanteId = solicitanteId;
        this.areaCampusId = areaCampusId;
        this.tipoOsId = tipoOsId;
        this.equipeId = equipeId;
        this.liderId = liderId;
        this.statusId = statusId;
        this.prioridade = prioridade;
        this.dataPrevista = dataPrevista;
        this.descricaoProblema = descricaoProblema;
    }

    public OrdemServico(int id, String numeroSequencial, int solicitanteId, int areaCampusId,
                        int tipoOsId, int equipeId, int liderId, int statusId,
                        int prioridade, LocalDateTime dataAbertura, LocalDate dataPrevista,
                        String descricaoProblema) {

        this.id = id;
        this.numeroSequencial = numeroSequencial;
        this.solicitanteId = solicitanteId;
        this.areaCampusId = areaCampusId;
        this.tipoOsId = tipoOsId;
        this.equipeId = equipeId;
        this.liderId = liderId;
        this.statusId = statusId;
        this.prioridade = prioridade;
        this.dataAbertura = dataAbertura;
        this.dataPrevista = dataPrevista;
        this.descricaoProblema = descricaoProblema;
    }

    // GETTERS E SETTERS

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumeroSequencial() { return numeroSequencial; }
    public void setNumeroSequencial(String numeroSequencial) { this.numeroSequencial = numeroSequencial; }

    public int getSolicitanteId() { return solicitanteId; }
    public void setSolicitanteId(int solicitanteId) { this.solicitanteId = solicitanteId; }

    public int getAreaCampusId() { return areaCampusId; }
    public void setAreaCampusId(int areaCampusId) { this.areaCampusId = areaCampusId; }

    public int getTipoOsId() { return tipoOsId; }
    public void setTipoOsId(int tipoOsId) { this.tipoOsId = tipoOsId; }

    public int getEquipeId() { return equipeId; }
    public void setEquipeId(int equipeId) { this.equipeId = equipeId; }

    public int getLiderId() { return liderId; }
    public void setLiderId(int liderId) { this.liderId = liderId; }

    public int getStatusId() { return statusId; }
    public void setStatusId(int statusId) { this.statusId = statusId; }

    public int getPrioridade() { return prioridade; }
    public void setPrioridade(int prioridade) { this.prioridade = prioridade; }

    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDate getDataPrevista() { return dataPrevista; }
    public void setDataPrevista(LocalDate dataPrevista) { this.dataPrevista = dataPrevista; }

    public String getDescricaoProblema() { return descricaoProblema; }
    public void setDescricaoProblema(String descricaoProblema) { this.descricaoProblema = descricaoProblema; }
}
