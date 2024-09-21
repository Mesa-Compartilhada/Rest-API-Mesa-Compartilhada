package com.pi.mesacompartilhada.models;

import com.fasterxml.jackson.annotation.*;
import com.pi.mesacompartilhada.records.response.DoacaoResponseDto;
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
    private Empresa empresaDoadora;
    @DBRef // Define a empresaDoadora como referencia ao documento doacoes
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

    public static DoacaoResponseDto doacaoToDoacaoResponseDto(Doacao doacao) {
        return new DoacaoResponseDto(
                doacao.id,
                doacao.getNome(),
                doacao.getDescricao(),
                doacao.getStatus(),
                doacao.getObservacao(),
                doacao.getDataPostada(),
                doacao.getDataEncerrada(),
                Empresa.empresaToEmpresaResponseDto(doacao.getEmpresaDoadora()),
                doacao.getEmpresaRecebedora() != null ? Empresa.empresaToEmpresaResponseDto(doacao.getEmpresaRecebedora()) : null
        );
    }

    public static DoacaoResponseDto doacaoToDoacaoResponseDtoSimples(Doacao doacao) {
        return new DoacaoResponseDto(
                doacao.id,
                doacao.getNome(),
                doacao.getDescricao(),
                doacao.getStatus(),
                doacao.getObservacao(),
                doacao.getDataPostada(),
                doacao.getDataEncerrada(),
                null,
                null
        );
    }
}
