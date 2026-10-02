package br.com.sistema.model;

public class Aluno {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String dataNascimento;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String plano;
    private String status;

    public Aluno() {}

    public Aluno(int id, String nome, String cpf, String email, String telefone, String dataNascimento, String endereco, String numero, String bairro, String cidade, String estado, String plano, String status) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.plano = plano;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPlano() { return plano; }
    public void setPlano(String plano) { this.plano = plano; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
