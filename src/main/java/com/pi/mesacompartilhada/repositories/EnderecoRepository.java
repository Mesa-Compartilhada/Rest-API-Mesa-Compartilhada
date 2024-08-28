package com.pi.mesacompartilhada.repositories;

import com.pi.mesacompartilhada.models.Endereco;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends MongoRepository<Endereco, String> {

    Optional<Endereco> findByCep(String cep);

}
