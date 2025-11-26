package br.ifce.sigej.model;

public class EquipeManutencao {

    private int id;
    private String nome;
    private String turno;

    public EquipeManutencao() {}

    public EquipeManutencao(String nome, String turno) {
        this.nome = nome;
        this.turno = turno;
    }

    // GETTERS E SETTERS
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getTurno() { return turno; }

    public void setTurno(String turno) { this.turno = turno; }
}
