package com.pi.mesacompartilhada.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pi.mesacompartilhada.enums.CategoriaEstabelecimento;
import com.pi.mesacompartilhada.enums.CategoriaInstituicao;
import com.pi.mesacompartilhada.enums.TipoEmpresa;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@Document("empresa")
public class Empresa {
    @Id
    private String id;
    private String cnpj;
    private Integer tipo;
    private Integer categoria;
    private String nome;
    private String email;
    private String senha;
    private String status;
    @DBRef
    private Endereco endereco;
    @DBRef // Define a lista de doacoes como referencia ao documento doacoes
    @JsonIgnoreProperties({"empresaDoadora", "empresaRecebedora"}) // Define as propriedades que serão ignoradas na lista de doações aninhadas
    private List<Doacao> doacoes;

    public Empresa(String id, String cnpj, TipoEmpresa tipo, Integer categoria, String nome, String email, String senha, String status, Endereco endereco, List<Doacao> doacoes) {
        this.id = id;
        this.cnpj = cnpj;
        this.tipo = tipo.getCodigo();
        this.categoria = validarCategoria(tipo, categoria);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.status = status;
        this.endereco = endereco;
        this.doacoes = doacoes;
    }

    public Empresa(String id, String cnpj, TipoEmpresa tipo, Integer categoria, String nome, String email, String senha, String status, Endereco endereco) {
        this.id = id;
        this.cnpj = cnpj;
        this.tipo = tipo.getCodigo();
        this.categoria = validarCategoria(tipo, categoria);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.status = status;
        this.endereco = endereco;
        this.doacoes = new ArrayList<>();
    }

    public Empresa(String cnpj, TipoEmpresa tipo, Integer categoria, String nome, String email, String senha, String status, Endereco endereco) {
        this.cnpj = cnpj;
        this.tipo = tipo.getCodigo();
        this.categoria = validarCategoria(tipo, categoria);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.status = status;
        this.endereco = endereco;
        this.doacoes = new ArrayList<>();
    }

    private Integer validarCategoria(TipoEmpresa tipo, Integer categoria) {
        if(tipo == TipoEmpresa.DOADORA) {
            return CategoriaEstabelecimento.valueOf(categoria).getCodigo();
        }
        else if(tipo == TipoEmpresa.RECEBEDORA) {
            return CategoriaInstituicao.valueOf(categoria).getCodigo();
        }
        else {
            throw new IllegalArgumentException("Tipo de empresa inválido");
        }
    }
}
