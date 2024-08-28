package com.pi.mesacompartilhada.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

public record EnderecoRecordDto(@NotBlank String cep,
                                @NotBlank String numero,
                                @NotBlank String logradouro,
                                @NotBlank String bairro,
                                @NotBlank String cidade,
                                @NotBlank String estado,
                                @NotBlank String pais,
                                String complemento) {
}
