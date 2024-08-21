package com.pi.mesacompartilhada.repositories;

import com.pi.mesacompartilhada.models.Doacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoacaoRepository extends MongoRepository<Doacao, String> {

}
