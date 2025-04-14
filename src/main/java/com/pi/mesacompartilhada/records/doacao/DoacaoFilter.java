package com.pi.mesacompartilhada.records.doacao;

import java.time.LocalDate;
import java.util.List;

public record DoacaoFilter(List<String> status,
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
                           List<Integer> tipoAlimento,
                           List<Integer> tipoArmazenamento,
                           String empresaDoadoraId,
                           String empresaRecebedoraId
                           ) {
}
