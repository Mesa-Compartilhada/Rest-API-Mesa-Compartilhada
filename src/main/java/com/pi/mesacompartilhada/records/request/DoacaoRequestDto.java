package com.pi.mesacompartilhada.records.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DoacaoRequestDto(
        @NotBlank(message = "O nome da doação não pode estar vazio")
        String nome,
        @NotBlank(message = "A descrição da doação não pode estar vazia")
        String descricao,
        @NotBlank(message = "A observação da doação não pode estar vazia")
        String observacao,
        @NotBlank(message = "A data em que a doação foi postada não pode estar vazia")
        String dataPostada,
        @NotBlank(message = "A data de encerramento da doação não pode estar vazia")
        String dataEncerrada,
        @NotNull(message = "O tipo do alimento não pode estar vazio")
        Integer tipoAlimento,
        @NotNull(message = "O tipo de armazenamento não pode estar vazio")
        Integer tipoArmazenamento,
        @NotBlank(message = "O ID da empresa doadora não pode estar vazio")
        String empresaDoadoraId
) {
}
