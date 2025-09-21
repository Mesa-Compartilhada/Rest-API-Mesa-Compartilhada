package com.pi.mesacompartilhada.models;

import com.pi.mesacompartilhada.enums.CategoriaEstabelecimento;
import com.pi.mesacompartilhada.enums.CategoriaInstituicao;
import com.pi.mesacompartilhada.enums.StatusEmpresa;
import com.pi.mesacompartilhada.enums.TipoEmpresa;
import com.pi.mesacompartilhada.records.empresa.EmpresaResponseDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;
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
    @Indexed(unique = true)
    private String cnpj;
    private Integer tipo;
    private Integer categoria;
    private String nome;
    @Indexed(unique = true)
    private String email;
    private String senha;
    private Integer status = StatusEmpresa.ATIVA.getCodigo();
    @DBRef
    private Endereco endereco;
    private String fotoPerfil;

    public Empresa(String id, String cnpj, TipoEmpresa tipo, Integer categoria, String nome, String email, String senha, Endereco endereco, String fotoPerfil) {
        this.id = id;
        this.cnpj = cnpj;
        this.tipo = tipo.getCodigo();
        this.categoria = validarCategoria(tipo, categoria);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.fotoPerfil = fotoPerfil;
    }

    public Empresa(String cnpj, TipoEmpresa tipo, Integer categoria, String nome, String email, String senha, Endereco endereco, String fotoPerfil) {
        this.cnpj = cnpj;
        this.tipo = tipo.getCodigo();
        this.categoria = validarCategoria(tipo, categoria);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.fotoPerfil = fotoPerfil;
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

    public String getNomeCategoria(Integer categoria) {
        if(this.tipo == 1) {
            return CategoriaEstabelecimento.valueOf(categoria).toString();
        }
        else {
            return CategoriaInstituicao.valueOf(categoria).toString();
        }
    }
}
