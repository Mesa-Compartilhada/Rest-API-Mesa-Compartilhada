package com.pi.mesacompartilhada.records.endereco;

public record EnderecoResponseDto(
        String id,
        String cep,
        String numero,
        String logradouro,
        String bairro,
        String cidade,
        String estado,
        String pais,
        String complemento,
        Double latitude,
        Double longitude

) {
}
