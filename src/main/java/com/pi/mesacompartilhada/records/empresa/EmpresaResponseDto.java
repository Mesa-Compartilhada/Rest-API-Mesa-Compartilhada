package com.pi.mesacompartilhada.records.empresa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pi.mesacompartilhada.records.doacao.DoacaoResponseDto;
import com.pi.mesacompartilhada.records.endereco.EnderecoResponseDto;

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
        String fotoPerfil) {
}
