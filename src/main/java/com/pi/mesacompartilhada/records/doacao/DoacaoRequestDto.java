package com.pi.mesacompartilhada.records.doacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record DoacaoRequestDto(
        @NotBlank(message = "O nome da doação não pode estar vazio")
        String nome,
        @NotBlank(message = "A descrição da doação não pode estar vazia")
        String descricao,
        @NotBlank(message = "A observação da doação não pode estar vazia")
        String observacao,
        @NotNull(message = "A data de fabricação do alimento doado não pode estar vazio")
        LocalDate dataFabricacao,
        @NotNull(message = "A data de validade do alimento doado não pode estar vazio")
        LocalDate dataValidade,
        @NotNull(message = "A data de criação da doação não pode estar vazia")
        LocalDate dataCriada,
        @NotNull(message = "A data máxima para retirada da doação não pode estar vazia")
        LocalDate dataMaxRetirada,
        @NotNull(message = "O horário mínimo de retirada da doação não pode estar vazio")
        LocalTime horarioMin,
        @NotNull(message = "O horário máximo de retirada da doação não pode estar vazio")
        LocalTime horarioMax,
        @NotNull(message = "O tipo do alimento não pode estar vazio")
        Integer tipoAlimento,
        @NotNull(message = "O tipo de armazenamento não pode estar vazio")
        Integer tipoArmazenamento,
        @NotBlank(message = "O ID da empresa doadora não pode estar vazio")
        String empresaDoadoraId,
        @NotNull(message="A quantidade não pode estar vazio")
        Double quantidade,
        @NotNull(message = "A unidade de medida não pode estar vazio")
        Integer unidadeMedida
) {
}
