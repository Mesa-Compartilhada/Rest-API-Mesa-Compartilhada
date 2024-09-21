package com.pi.mesacompartilhada.records.request;

import jakarta.validation.constraints.NotBlank;

public record DoacaoRequestDto(
        @NotBlank(message = "O nome da doação não pode estar vazio")
        String nome,
        @NotBlank(message = "A descrição da doação não pode estar vazia")
        String descricao,
        @NotBlank(message = "O status da doação não pode estar vazio")
        String status,
        @NotBlank(message = "A observação da doação não pode estar vazia")
        String observacao,
        @NotBlank(message = "A data em que a doação foi postada não pode estar vazia")
        String dataPostada,
        @NotBlank(message = "A data de encerramento da doação não pode estar vazia")
        String dataEncerrada,
        @NotBlank(message = "O ID da empresa doadora não pode estar vazio")
        String empresaDoadoraId,
        String empresaRecebedoraId
) {
}
