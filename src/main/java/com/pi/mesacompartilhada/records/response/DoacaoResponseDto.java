package com.pi.mesacompartilhada.records.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public record DoacaoResponseDto(
        String id,
        String nome,
        String descricao,
        String status,
        String observacao,
        String dataPostada,
        String dataEncerrada,
        @JsonIgnoreProperties("doacoes")
        EmpresaResponseDto empresaDoadora,
        @JsonIgnoreProperties("doacoes")
        EmpresaResponseDto empresaRecebedora
) {
}
