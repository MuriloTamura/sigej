package br.ifce.sigej.model;

public class Pessoa {

    private int id;
    private String nome;
    private String cpf;
    private String matriculaSiape;
    private String email;
    private String telefone;
    private boolean ativo;

    public Pessoa() {}

    public Pessoa(String nome, String cpf, String matriculaSiape, String email, String telefone, boolean ativo) {
        this.nome = nome;
        this.cpf = cpf;
        this.matriculaSiape = matriculaSiape;
        this.email = email;
        this.telefone = telefone;
        this.ativo = ativo;
    }

    public Pessoa(int id, String nome, String cpf, String matriculaSiape, String email, String telefone, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.matriculaSiape = matriculaSiape;
        this.email = email;
        this.telefone = telefone;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getMatriculaSiape() {
        return matriculaSiape;
    }

    public void setMatriculaSiape(String matriculaSiape) {
        this.matriculaSiape = matriculaSiape;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
