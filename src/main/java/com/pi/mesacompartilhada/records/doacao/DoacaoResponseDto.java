package com.pi.mesacompartilhada.records.doacao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pi.mesacompartilhada.records.empresa.EmpresaResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;

public record DoacaoResponseDto(
        String id,
        String nome,
        String descricao,
        String status,
        String observacao,
        LocalDate dataFabricacao,
        LocalDate dataValidade,
        LocalDate dataCriada,
        LocalDate dataEncerrada,
        LocalDate dataMaxRetirada,
        LocalTime horarioMin,
        LocalTime horarioMax,
        String tipoAlimento,
        String tipoArmazenamento,
        @JsonIgnoreProperties("doacoes")
        EmpresaResponseDto empresaDoadora,
        @JsonIgnoreProperties("doacoes")
        EmpresaResponseDto empresaRecebedora,
        boolean empresaDoadoraConcluida,
        boolean empresaRecebedoraConcluida,
        Double quantidade,
        String unidadeMedida

) {
}
