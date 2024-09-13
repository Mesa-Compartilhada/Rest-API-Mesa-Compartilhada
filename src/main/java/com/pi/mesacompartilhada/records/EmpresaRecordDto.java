package com.pi.mesacompartilhada.records;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmpresaRecordDto(@NotBlank String cnpj,
                               @NotNull Integer tipo,
                               @NotNull Integer categoria,
                               @NotBlank String nome,
                               @NotBlank @Email String email,
                               @NotBlank String senha,
                               @NotBlank String status,
                               @NotBlank String enderecoId) {
}
