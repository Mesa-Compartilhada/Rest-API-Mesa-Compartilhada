package com.pi.mesacompartilhada.mapper;

import com.pi.mesacompartilhada.models.Empresa;
import com.pi.mesacompartilhada.records.empresa.EmpresaResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    EmpresaResponseDto empresaToEmpresaDto(Empresa empresa);

}
