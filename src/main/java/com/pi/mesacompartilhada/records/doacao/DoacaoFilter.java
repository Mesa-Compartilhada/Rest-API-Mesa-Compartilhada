package com.pi.mesacompartilhada.records.doacao;

import java.time.LocalDate;

public record DoacaoFilter(String status,
                           LocalDate dataFabricacaoMin,
                           LocalDate dataFabricacaoMax,
                           LocalDate dataValidadeMin,
                           LocalDate dataValidadeMax,
                           LocalDate dataCriadaMin,
                           LocalDate dataCriadaMax,
                           LocalDate dataEncerradaMin,
                           LocalDate dataEncerradaMax,
                           LocalDate dataRetiradaMin,
                           LocalDate dataRetiradaMax,
                           Integer tipoAlimento,
                           Integer tipoArmazenamento
                           ) {
}
