package com.ftconsult.scraper.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String codigoDoChamado;

    @Column(columnDefinition="text")
    private String dataDeResposta;
    @Column(columnDefinition="text")
    private String dataDeFinalizacaoDoChamado;
    @Column(columnDefinition="text")
    private String empresa;
    @Column(columnDefinition="text")
    private String solicitante;
    @Column(columnDefinition="text")
    private String operador;
    @Column(columnDefinition="text")
    private String grupo;
    @Column(columnDefinition="text")
    private String satisfacaoCordialidade;
    @Column(columnDefinition="text")
    private String satisfacaoTempoSolucao;
    @Column(columnDefinition="text")
    private String satisfacaoSolucaoAplicada;
    @Column(columnDefinition="text")
    private String probabilidadeRecomendar;
    @Column(columnDefinition="text")
    private String comentario;

    public Atendimento(String codigoDoChamado, String dataDeResposta, String dataDeFinalizacaoDoChamado, String empresa, String solicitante, String operador, String grupo, String satisfacaoCordialidade, String satisfacaoTempoSolucao, String satisfacaoSolucaoAplicada, String probabilidadeRecomendar, String comentario) {
        this.codigoDoChamado = codigoDoChamado;
        this.dataDeResposta = dataDeResposta;
        this.dataDeFinalizacaoDoChamado = dataDeFinalizacaoDoChamado;
        this.empresa = empresa;
        this.solicitante = solicitante;
        this.operador = operador;
        this.grupo = grupo;
        this.satisfacaoCordialidade = satisfacaoCordialidade;
        this.satisfacaoTempoSolucao = satisfacaoTempoSolucao;
        this.satisfacaoSolucaoAplicada = satisfacaoSolucaoAplicada;
        this.probabilidadeRecomendar = probabilidadeRecomendar;
        this.comentario = comentario;
    }

}
