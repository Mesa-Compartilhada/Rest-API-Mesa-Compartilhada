package com.pi.mesacompartilhada.records.request;

import com.pi.mesacompartilhada.models.Doacao;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

public record EmpresaRequestDto(
        @NotBlank(message = "O CNPJ não pode estar vazio")
        @CNPJ(message = "O CNPJ deve ser válido")
        String cnpj,
        @NotNull(message = "O tipo da empresa não pode estar vazio")
        Integer tipo,
        @NotNull(message = "A categoria da empresa não pode estar vazio")
        Integer categoria,
        @NotBlank(message = "O nome da empresa não pode estar vazio")
        String nome,
        @NotBlank(message = "O email não pode estar vazio")
        @Email(message = "O email deve ser válido")
        String email,
        @NotBlank(message = "A senha não pode estar vazia")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        String senha,
        @NotNull(message = "O status da empresa não pode estar vazio")
        Integer status,
        @NotBlank(message = "O ID do endereço não pode estar vazio")
        String enderecoId,
        List<Doacao> doacoes
) {
}
