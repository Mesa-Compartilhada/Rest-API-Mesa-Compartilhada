package com.pi.mesacompartilhada.mapper;

import com.pi.mesacompartilhada.enums.UnidadeMedida;
import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.records.doacao.DoacaoResponseDto;
import com.pi.mesacompartilhada.states.StateDoacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DoacaoMapper {

    @Mapping(target = "quantidade", source = "quantidade", qualifiedByName = "mapQuantidade")
    @Mapping(target = "unidadeMedida", source = "doacao", qualifiedByName = "mapUnidadeMedida")
    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    DoacaoResponseDto doacaoToDoacaoDto(Doacao doacao);

    @Named("mapStatus")
    default String mapStatus(StateDoacao status) {
        return status.getStateName();
    }

    @Named("mapQuantidade")
    default Double mapQuantidade(Double quantidade) {
        if(quantidade >= 1000) {
            return quantidade / 1000;
        }
        return quantidade;
    }

    @Named("mapUnidadeMedida")
    default Integer mapUnidadeMedida(Doacao doacao) {
        if(doacao.getQuantidade() >= 1000) {
            if(doacao.getUnidadeMedida() == UnidadeMedida.MILILITRO.getCodigo()) {
                return UnidadeMedida.LITRO.getCodigo();
            }
            else {
                return UnidadeMedida.QUILO.getCodigo();
            }
        }
        return doacao.getUnidadeMedida();
    }
}
