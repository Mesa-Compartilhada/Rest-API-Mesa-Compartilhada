package com.pi.mesacompartilhada.models;

import com.pi.mesacompartilhada.records.response.EnderecoResponseDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("endereco")
public class Endereco {

    @Id
    private String id;
    private String cep;
    private String numero;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private String complemento;

    public Endereco(String cep, String numero, String logradouro, String bairro, String cidade, String estado, String pais, String complemento) {
        this.cep = cep;
        this.numero = numero;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.complemento = complemento;
    }

    static public EnderecoResponseDto enderecoToEnderecoResponseDto(Endereco endereco) {
        return new EnderecoResponseDto(
                endereco.getId(),
                endereco.getCep(),
                endereco.getNumero(),
                endereco.getLogradouro(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getPais(),
                endereco.getComplemento()
        );
    }
}
