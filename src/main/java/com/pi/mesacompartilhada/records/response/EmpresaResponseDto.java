package com.pi.mesacompartilhada.records.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public record EmpresaResponseDto(
        String id,
        String cnpj,
        Integer tipo,
        Integer categoria,
        String nome,
        String email,
        Integer status,
        EnderecoResponseDto endereco,
        @JsonIgnoreProperties({"empresaDoadora", "empresaRecebedora"})
        List<DoacaoResponseDto> doacoes) {
}
