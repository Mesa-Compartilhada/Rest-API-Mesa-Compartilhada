package com.pi.mesacompartilhada.mapper;

import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.records.doacao.DoacaoResponseDto;
import com.pi.mesacompartilhada.states.StateDoacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DoacaoMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    DoacaoResponseDto doacaoToDoacaoDto(Doacao doacao);

    @Named("mapStatus")
    default String mapStatus(StateDoacao status) {
        return status.getStateName();
    }

}
