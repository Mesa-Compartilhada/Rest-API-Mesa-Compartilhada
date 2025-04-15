package com.pi.mesacompartilhada.records.empresa;

import jakarta.validation.constraints.NotBlank;

public record EmpresaResetPasswordDto(
        @NotBlank(message="O token não pode estar vazio")
        String token,
        @NotBlank(message="A senha não pode estar vazia")
        String senha
){ }
