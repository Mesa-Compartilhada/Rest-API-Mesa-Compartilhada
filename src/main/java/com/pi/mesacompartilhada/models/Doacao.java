package com.pi.mesacompartilhada.models;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="doacao")
public class Doacao {

    @Id
    private String id;

    private String nome;
    private String descricao;
    private String status;
    private String observacao;
    private String dataPostada;
    private String dataEncerrada;
    @DBRef // Define a empresaDoadora como referencia ao documento doacoes
    @JsonIgnoreProperties("doacoes") // Define as propriedades que serão ignoradas na empresaDoadora aninhada
    private Empresa empresaDoadora;
    @DBRef // Define a empresaDoadora como referencia ao documento doacoes
    @JsonIgnoreProperties("doacoes") // Define as propriedades que serão ignoradas na empresaRecebedora aninhada
    private Empresa empresaRecebedora;

    public Doacao(String nome, String descricao, String status, String observacao, String dataPostada, String dataEncerrada, Empresa empresaDoadora) {
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
        this.observacao = observacao;
        this.dataPostada = dataPostada;
        this.dataEncerrada = dataEncerrada;
        this.empresaDoadora = empresaDoadora;
    }
}
