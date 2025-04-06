package com.pi.mesacompartilhada.repositories;

import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.records.doacao.DoacaoFilter;

import java.util.List;

public interface CustomDoacaoRepository {

    List<Doacao> findByFilter(DoacaoFilter filter);

}
