package com.pi.mesacompartilhada.records.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoRequestDto(
        @NotBlank(message = "O CEP não pode estar vazio")
        String cep,
        @NotBlank(message = "O número não pode estar vazio")
        String numero,
        @NotBlank(message = "O logradouro não pode estar vazio")
        String logradouro,
        @NotBlank(message = "O bairro não pode estar vazio")
        String bairro,
        @NotBlank(message = "A cidade não pode estar vazia")
        String cidade,
        @NotBlank(message = "O estado não pode estar vazio")
        String estado,
        @NotBlank(message = "O país não pode estar vazio")
        String pais,
        String complemento,
        @NotNull (message = "A latitude não pode estar vazia")
        Double latitude,
        @NotNull (message = "A longitude não pode estar vazia")
        Double longitude
) {
}
