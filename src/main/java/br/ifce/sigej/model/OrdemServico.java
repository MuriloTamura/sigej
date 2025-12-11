package br.ifce.sigej.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrdemServico {

    private int id;
    private String numeroSequencial;
    private Integer solicitanteId;
    private Integer areaCampusId;
    private Integer tipoOsId;
    private Integer equipeId;
    private Integer liderId;
    private Integer statusId;
    private Integer prioridade;
    private LocalDateTime dataAbertura;
    private LocalDate dataPrevista;
    private String descricaoProblema;

    // Objetos relacionados para exibição
    private Pessoa solicitante;
    private AreaCampus areaCampus;
    private TipoOrdemServico tipoOs;
    private EquipeManutencao equipe;
    private Funcionario lider;
    private StatusOrdemServico status;

    public OrdemServico() {}

    public OrdemServico(String numeroSequencial, Integer solicitanteId, Integer areaCampusId,
                        Integer tipoOsId, Integer equipeId, Integer liderId, Integer statusId,
                        Integer prioridade, LocalDate dataPrevista, String descricaoProblema) {

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

    public OrdemServico(int id, String numeroSequencial, Integer solicitanteId, Integer areaCampusId,
                        Integer tipoOsId, Integer equipeId, Integer liderId, Integer statusId,
                        Integer prioridade, LocalDateTime dataAbertura, LocalDate dataPrevista,
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

    public Integer getSolicitanteId() { return solicitanteId; }
    public void setSolicitanteId(Integer solicitanteId) { this.solicitanteId = solicitanteId; }

    public Integer getAreaCampusId() { return areaCampusId; }
    public void setAreaCampusId(Integer areaCampusId) { this.areaCampusId = areaCampusId; }

    public Integer getTipoOsId() { return tipoOsId; }
    public void setTipoOsId(Integer tipoOsId) { this.tipoOsId = tipoOsId; }

    public Integer getEquipeId() { return equipeId; }
    public void setEquipeId(Integer equipeId) { this.equipeId = equipeId; }

    public Integer getLiderId() { return liderId; }
    public void setLiderId(Integer liderId) { this.liderId = liderId; }

    public Integer getStatusId() { return statusId; }
    public void setStatusId(Integer statusId) { this.statusId = statusId; }

    public Integer getPrioridade() { return prioridade; }
    public void setPrioridade(Integer prioridade) { this.prioridade = prioridade; }

    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDate getDataPrevista() { return dataPrevista; }
    public void setDataPrevista(LocalDate dataPrevista) { this.dataPrevista = dataPrevista; }

    public String getDescricaoProblema() { return descricaoProblema; }
    public void setDescricaoProblema(String descricaoProblema) { this.descricaoProblema = descricaoProblema; }

    // Getters e Setters dos objetos relacionados

    public Pessoa getSolicitante() { return solicitante; }
    public void setSolicitante(Pessoa solicitante) { this.solicitante = solicitante; }

    public AreaCampus getAreaCampus() { return areaCampus; }
    public void setAreaCampus(AreaCampus areaCampus) { this.areaCampus = areaCampus; }

    public TipoOrdemServico getTipoOs() { return tipoOs; }
    public void setTipoOs(TipoOrdemServico tipoOs) { this.tipoOs = tipoOs; }

    public EquipeManutencao getEquipe() { return equipe; }
    public void setEquipe(EquipeManutencao equipe) { this.equipe = equipe; }

    public Funcionario getLider() { return lider; }
    public void setLider(Funcionario lider) { this.lider = lider; }

    public StatusOrdemServico getStatus() { return status; }
    public void setStatus(StatusOrdemServico status) { this.status = status; }
}