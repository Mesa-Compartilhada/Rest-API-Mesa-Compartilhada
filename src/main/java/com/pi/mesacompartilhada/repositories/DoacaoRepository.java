package com.pi.mesacompartilhada.repositories;

import com.pi.mesacompartilhada.models.Doacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoacaoRepository extends MongoRepository<Doacao, String> {

    @Query(value = "{ 'empresaDoadoraId' : ?0 }")
    List<Doacao> findByEmpresaDoadoraId(String empresaDoadoraId);

    @Query(value = "{ 'empresaRecebedoraId' : ?0 }")
    List<Doacao> findByEmpresaRecebedoraId(String empresaRecebedoraId);

    @Query(value = "{ 'status' : ?0 }")
    List<Doacao> findByStatus(String status);

}
