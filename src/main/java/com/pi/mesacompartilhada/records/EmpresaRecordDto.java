package com.pi.mesacompartilhada.records;

import com.pi.mesacompartilhada.models.Doacao;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EmpresaRecordDto(@NotBlank String cnpj,
                               @NotNull Integer tipo,
                               @NotNull Integer categoria,
                               @NotBlank String nome,
                               @NotBlank @Email String email,
                               @NotBlank String senha,
                               @NotBlank Integer status,
                               @NotBlank String enderecoId,
                               List<Doacao> doacoes) {
}
