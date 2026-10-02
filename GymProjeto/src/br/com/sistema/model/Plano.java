package br.com.sistema.model;

public class Plano {
    private int id;
    private String nome;
    private int duracaoMeses;
    private double valor;
    private String situacao;
    private String descricao;

    public Plano() {}

    public Plano(int id, String nome, int duracaoMeses, double valor, String situacao, String descricao) {
        this.id = id;
        this.nome = nome;
        this.duracaoMeses = duracaoMeses;
        this.valor = valor;
        this.situacao = situacao;
        this.descricao = descricao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getDuracaoMeses() { return duracaoMeses; }
    public void setDuracaoMeses(int duracaoMeses) { this.duracaoMeses = duracaoMeses; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return nome;
    }
}
