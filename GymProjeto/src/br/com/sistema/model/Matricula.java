package br.com.sistema.model;

import java.time.LocalDate;

public class Matricula {

    private int id;
    private int alunoId;
    private int planoId;
    private LocalDate dataInicio;
    private LocalDate dataVencimento;
    private double valor;
    private String situacao;

    public Matricula() {}

    public Matricula(int id, int alunoId, int planoId, LocalDate dataInicio,
            LocalDate dataVencimento, double valor, String situacao) {
        this.id = id;
        this.alunoId = alunoId;
        this.planoId = planoId;
        this.dataInicio = dataInicio;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.situacao = situacao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }

    public int getPlanoId() { return planoId; }
    public void setPlanoId(int planoId) { this.planoId = planoId; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }
}
