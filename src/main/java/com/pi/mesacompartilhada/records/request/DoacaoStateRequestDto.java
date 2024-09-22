package com.pi.mesacompartilhada.records.request;

import jakarta.validation.constraints.NotBlank;

public record DoacaoStateRequestDto(
        @NotBlank(message = "O status da doação não pode estar vazio")
        String status,
        String empresaRecebedoraId
) {
}
