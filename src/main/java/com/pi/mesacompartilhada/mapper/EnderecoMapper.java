package com.pi.mesacompartilhada.mapper;

import com.pi.mesacompartilhada.models.Endereco;
import com.pi.mesacompartilhada.records.endereco.EnderecoResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoResponseDto enderecoToEnderecoDto(Endereco endereco);

}