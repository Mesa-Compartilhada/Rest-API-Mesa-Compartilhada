package com.pi.mesacompartilhada.models;

import com.fasterxml.jackson.annotation.*;
import com.pi.mesacompartilhada.records.response.DoacaoResponseDto;
import com.pi.mesacompartilhada.states.*;
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

    public Doacao(String nome, String descricao, String observacao, String dataPostada, String dataEncerrada, Empresa empresaDoadora) {
        this.nome = nome;
        this.descricao = descricao;
        this.observacao = observacao;
        this.dataPostada = dataPostada;
        this.dataEncerrada = dataEncerrada;
        this.empresaDoadora = empresaDoadora;

        this.status = "Disponivel";
    }

    public Doacao(String id, String nome, String descricao, String observacao, String dataPostada, String dataEncerrada, Empresa empresaDoadora, Empresa empresaRecebedora) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.observacao = observacao;
        this.dataPostada = dataPostada;
        this.dataEncerrada = dataEncerrada;
        this.empresaDoadora = empresaDoadora;
        this.empresaRecebedora = empresaRecebedora;

        this.status = "Disponivel";
    }

    public StateDoacao getStatus() {
        return switch (this.status) {
            case "Disponivel" -> new Disponivel(this);
            case "Andamento" -> new Andamento(this);
            case "Concluida" -> new Concluida(this);
            case "Cancelada" -> new Cancelada(this);
            default -> throw new IllegalArgumentException("Status de doação inválido");
        };
    }

    public static DoacaoResponseDto doacaoToDoacaoResponseDto(Doacao doacao) {
        return new DoacaoResponseDto(
                doacao.getId(),
                doacao.getNome(),
                doacao.getDescricao(),
                doacao.getStatus().getStateName(),
                doacao.getObservacao(),
                doacao.getDataPostada(),
                doacao.getDataEncerrada(),
                Empresa.empresaToEmpresaResponseDto(doacao.getEmpresaDoadora()),
                doacao.getEmpresaRecebedora() != null ? Empresa.empresaToEmpresaResponseDto(doacao.getEmpresaRecebedora()) : null
        );
    }

    public static DoacaoResponseDto doacaoToDoacaoResponseDtoSimples(Doacao doacao) {
        return new DoacaoResponseDto(
                doacao.getId(),
                doacao.getNome(),
                doacao.getDescricao(),
                doacao.getStatus().getStateName(),
                doacao.getObservacao(),
                doacao.getDataPostada(),
                doacao.getDataEncerrada(),
                null,
                null
        );
    }
}
