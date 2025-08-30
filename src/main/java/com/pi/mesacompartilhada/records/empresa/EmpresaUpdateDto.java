package com.pi.mesacompartilhada.records.empresa;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record EmpresaUpdateDto(
        @CNPJ(message = "O CNPJ deve ser válido")
        String cnpj,
        Integer tipo,
        Integer categoria,
        String nome,
        @Email(message = "O email deve ser válido")
        String email,
        String enderecoId,
        String fotoPerfil
) {
}
