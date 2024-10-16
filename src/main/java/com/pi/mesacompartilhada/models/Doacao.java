package com.pi.mesacompartilhada.models;

import com.pi.mesacompartilhada.enums.TipoAlimento;
import com.pi.mesacompartilhada.enums.TipoArmazenamento;
import com.pi.mesacompartilhada.records.response.DoacaoResponseDto;
import com.pi.mesacompartilhada.states.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private LocalDate dataFabricacao;
    private LocalDate dataValidade;
    private LocalDate dataCriada;
    private LocalDate dataEncerrada;
    private LocalDate dataMaxRetirada;
    private LocalTime horarioMin;
    private LocalTime horarioMax;
    private Integer tipoAlimento;
    private Integer tipoArmazenamento;
    @DBRef // Define a empresaDoadora como referencia ao documento doacoes
    private Empresa empresaDoadora;
    @DBRef // Define a empresaDoadora como referencia ao documento doacoes
    private Empresa empresaRecebedora;
    private boolean empresaDoadoraConcluida = false;
    private boolean empresaRecebedoraConcluida = false;

    public Doacao(String nome, String descricao, String observacao, LocalDate dataFabricacao, LocalDate dataValidade, LocalDate dataCriada, LocalDate dataMaxRetirada, LocalTime horarioMin, LocalTime horarioMax, TipoAlimento tipoAlimento, TipoArmazenamento tipoArmazenamento, Empresa empresaDoadora) {
        this.nome = nome;
        this.descricao = descricao;
        this.observacao = observacao;
        this.dataFabricacao = dataFabricacao;
        this.dataValidade = dataValidade;
        this.dataCriada = dataCriada;
        this.dataEncerrada = null;
        this.dataMaxRetirada = dataMaxRetirada;
        this.horarioMin = horarioMin;
        this.horarioMax = horarioMax;
        this.tipoAlimento = tipoAlimento.getCodigo();
        this.tipoArmazenamento = tipoArmazenamento.getCodigo();
        this.empresaDoadora = empresaDoadora;

        this.status = "DISPONIVEL";
    }

    public Doacao(String id, String nome, String descricao, String observacao, LocalDate dataFabricacao, LocalDate dataValidade, LocalDate dataCriada, LocalDate dataMaxRetirada, LocalTime horarioMin, LocalTime horarioMax, TipoAlimento tipoAlimento, TipoArmazenamento tipoArmazenamento, Empresa empresaDoadora, Empresa empresaRecebedora) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.observacao = observacao;
        this.dataFabricacao = dataFabricacao;
        this.dataValidade = dataValidade;
        this.dataCriada = dataCriada;
        this.dataEncerrada = null;
        this.dataMaxRetirada = dataMaxRetirada;
        this.horarioMin = horarioMin;
        this.horarioMax = horarioMax;
        this.tipoAlimento = tipoAlimento.getCodigo();
        this.tipoArmazenamento = tipoArmazenamento.getCodigo();
        this.empresaDoadora = empresaDoadora;
        this.empresaRecebedora = empresaRecebedora;

        this.status = "DISPONIVEL";
    }

    public StateDoacao getStatus() {
        return switch (this.status) {
            case "DISPONIVEL" -> new Disponivel(this);
            case "ANDAMENTO" -> new Andamento(this);
            case "CONCLUIDA" -> new Concluida(this);
            case "CANCELADA" -> new Cancelada(this);
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
                doacao.getDataFabricacao(),
                doacao.getDataValidade(),
                doacao.getDataCriada(),
                doacao.getDataEncerrada(),
                doacao.getDataMaxRetirada(),
                doacao.getHorarioMin(),
                doacao.getHorarioMax(),
                TipoAlimento.valueOf(doacao.getTipoAlimento()).toString(),
                TipoArmazenamento.valueOf(doacao.getTipoArmazenamento()).toString(),
                Empresa.empresaToEmpresaResponseDto(doacao.getEmpresaDoadora()),
                doacao.getEmpresaRecebedora() != null ? Empresa.empresaToEmpresaResponseDto(doacao.getEmpresaRecebedora()) : null,
                doacao.isEmpresaDoadoraConcluida(),
                doacao.isEmpresaRecebedoraConcluida()
        );
    }

    public static DoacaoResponseDto doacaoToDoacaoResponseDtoSimples(Doacao doacao) {
        return new DoacaoResponseDto(
                doacao.getId(),
                doacao.getNome(),
                doacao.getDescricao(),
                doacao.getStatus().getStateName(),
                doacao.getObservacao(),
                doacao.getDataFabricacao(),
                doacao.getDataValidade(),
                doacao.getDataCriada(),
                doacao.getDataEncerrada(),
                doacao.getDataMaxRetirada(),
                doacao.getHorarioMin(),
                doacao.getHorarioMax(),
                TipoAlimento.valueOf(doacao.getTipoAlimento()).toString(),
                TipoArmazenamento.valueOf(doacao.getTipoArmazenamento()).toString(),
                null,
                null,
                doacao.isEmpresaDoadoraConcluida(),
                doacao.isEmpresaDoadoraConcluida()
        );
    }
}
