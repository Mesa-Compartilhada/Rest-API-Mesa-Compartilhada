package com.pi.mesacompartilhada.records;

import jakarta.validation.constraints.NotBlank;

public record DoacaoRecordDto(@NotBlank String nome,
                              @NotBlank String descricao,
                              @NotBlank String status,
                              @NotBlank String observacao,
                              @NotBlank String dataPostada,
                              @NotBlank String dataEncerrada,
                              String empresaDoadora,
                              String empresaRecebedora) {
}
