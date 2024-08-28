package com.pi.mesacompartilhada.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("empresa")
public class Empresa {

    @Id
    private String id;
    private String cnpj;
    private int tipo;
    private String nome;
    private String email;
    private String senha;
    private String status;
    private Endereco endereco;

    public Empresa(String cnpj, int tipo, String nome, String email, String senha, String status, Endereco endereco) {
        this.cnpj = cnpj;
        this.tipo = tipo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.status = status;
        this.endereco = endereco;
    }

}
