package com.pi.mesacompartilhada.records.empresa;

import jakarta.validation.constraints.NotBlank;

public record EmpresaUpdatePasswordDto(
        @NotBlank(message="A senha atual não pode estar vazia")
        String senhaAtual,
        @NotBlank(message="A nova senha não pode estar vazia")
        String senhaNova
) {
}
