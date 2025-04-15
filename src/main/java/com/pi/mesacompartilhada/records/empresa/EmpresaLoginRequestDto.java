package com.pi.mesacompartilhada.records.empresa;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmpresaLoginRequestDto(
        @NotBlank(message = "O email não pode estar vazio")
        @Email(message = "O email deve ser válido")
        String email,
        @NotBlank(message = "A senha não pode estar vazia")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        String senha
) {
}
