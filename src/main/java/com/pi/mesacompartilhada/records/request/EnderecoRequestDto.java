package com.pi.mesacompartilhada.records.request;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRequestDto(
        @NotBlank(message = "O CEP não pode estar vazio")
        String cep,
        @NotBlank(message = "O número não pode estar vazio")
        String numero,
        @NotBlank(message = "O logradouro não pode estar vazio")
        String logradouro,
        @NotBlank(message = "O bairro não pode estar vazio")
        String bairro,
        @NotBlank(message = "A cidade não pode estar vazio")
        String cidade,
        @NotBlank(message = "O estado não pode estar vazio")
        String estado,
        @NotBlank(message = "O país não pode estar vazio")
        String pais,
        String complemento
) {
}
