package com.eventorganizer.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome_evento", nullable = false)
    private String nomeEvento;

    @Column(name = "descricao", length = 250)
    private String descricao;

    @Column(name = "local_evento", length = 250)
    private String local;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "faixa_etaria", length = 20)
    private String faixaEtaria;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_periodicidade", nullable = false, length = 10)
    private Periodicidade periodicidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_espaco", nullable = false, length = 10)
    private Espaco espaco;

    @Column(name = "capacidade")
    private int capacidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_entrada", nullable = false, length = 10)
    private Entrada entrada;

    @ManyToOne
    @JoinColumn(name = "dono_id", nullable = false)
    private Usuario dono;

    public Evento() {}

    public Evento(String nomeEvento, String descricao, String local, LocalDateTime dataHora, String faixaEtaria,
                 Periodicidade periodicidade, Espaco espaco, int capacidade, Entrada entrada, Usuario dono) {
        this.nomeEvento = nomeEvento;
        this.descricao = descricao;
        this.local = local;
        this.dataHora = dataHora;
        this.faixaEtaria = faixaEtaria;
        this.periodicidade = periodicidade;
        this.espaco = espaco;
        this.capacidade = capacidade;
        this.entrada = entrada;
        this.dono = dono;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public Periodicidade getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(Periodicidade periodicidade) {
        this.periodicidade = periodicidade;
    }

    public Espaco getEspaco() {
        return espaco;
    }

    public void setEspaco(Espaco espaco) {
        this.espaco = espaco;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public Usuario getDono() {
        return dono;
    }

    public void setDono(Usuario dono) {
        this.dono = dono;
    }
}
