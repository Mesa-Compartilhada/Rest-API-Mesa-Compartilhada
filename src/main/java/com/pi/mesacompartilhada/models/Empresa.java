package com.pi.mesacompartilhada.models;

import com.pi.mesacompartilhada.enums.CategoriaEstabelecimento;
import com.pi.mesacompartilhada.enums.CategoriaInstituicao;
import com.pi.mesacompartilhada.enums.StatusEmpresa;
import com.pi.mesacompartilhada.enums.TipoEmpresa;
import com.pi.mesacompartilhada.records.doacao.DoacaoResponseDto;
import com.pi.mesacompartilhada.records.empresa.EmpresaResponseDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
    @DBRef // Define a lista de doacoes como referencia ao documento doacoes
    private List<Doacao> doacoes;

    public Empresa(String id, String cnpj, TipoEmpresa tipo, Integer categoria, String nome, String email, String senha, Endereco endereco, List<Doacao> doacoes) {
        this.id = id;
        this.cnpj = cnpj;
        this.tipo = tipo.getCodigo();
        this.categoria = validarCategoria(tipo, categoria);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.doacoes = doacoes;
    }

    public Empresa(String id, String cnpj, TipoEmpresa tipo, Integer categoria, String nome, String email, String senha, Endereco endereco) {
        this.id = id;
        this.cnpj = cnpj;
        this.tipo = tipo.getCodigo();
        this.categoria = validarCategoria(tipo, categoria);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.doacoes = new ArrayList<>();
    }

    public Empresa(String cnpj, TipoEmpresa tipo, Integer categoria, String nome, String email, String senha, Endereco endereco) {
        this.cnpj = cnpj;
        this.tipo = tipo.getCodigo();
        this.categoria = validarCategoria(tipo, categoria);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
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

    public String getNomeCategoria(Integer categoria) {
        if(this.tipo == 1) {
            return CategoriaEstabelecimento.valueOf(categoria).toString();
        }
        else {
            return CategoriaInstituicao.valueOf(categoria).toString();
        }
    }

    public static EmpresaResponseDto empresaToEmpresaResponseDto(Empresa empresa) {
        List<DoacaoResponseDto> doacoes = new ArrayList<>();
        for(Doacao doacao : empresa.getDoacoes()) {
            if(doacao != null) {
                doacoes.add(Doacao.doacaoToDoacaoResponseDtoSimples(doacao));
            }
        }
        return new EmpresaResponseDto(
                empresa.getId(),
                empresa.getCnpj(),
                TipoEmpresa.valueOf(empresa.getTipo()).toString(),
                empresa.getNomeCategoria(empresa.getCategoria()),
                empresa.getNome(),
                empresa.getEmail(),
                StatusEmpresa.valueOf(empresa.getStatus()).toString(),
                Endereco.enderecoToEnderecoResponseDto(empresa.getEndereco()),
                doacoes
        );
    }

    public static EmpresaResponseDto empresaToEmpresaResponseDtoSimples(Empresa empresa) {
        return new EmpresaResponseDto(
                empresa.getId(),
                empresa.getCnpj(),
                TipoEmpresa.valueOf(empresa.getTipo()).toString(),
                empresa.getNomeCategoria(empresa.getCategoria()),
                empresa.getNome(),
                empresa.getEmail(),
                StatusEmpresa.valueOf(empresa.getStatus()).toString(),
                Endereco.enderecoToEnderecoResponseDto(empresa.getEndereco()),
                null
        );
    }
}
