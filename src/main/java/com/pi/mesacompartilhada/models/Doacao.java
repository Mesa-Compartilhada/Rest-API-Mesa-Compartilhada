package com.pi.mesacompartilhada.models;

import lombok.*;
import org.springframework.data.annotation.Id;
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
    private String empresaDoadora; // Criar classe empresa
    private String empresaRecebedora;

    public Doacao(String nome, String descricao, String status, String observacao, String dataPostada, String dataEncerrada, String empresaDoadora, String empresaRecebedora) {
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
        this.observacao = observacao;
        this.dataPostada = dataPostada;
        this.dataEncerrada = dataEncerrada;
        this.empresaDoadora = empresaDoadora;
        this.empresaRecebedora = empresaRecebedora;
    }
}
