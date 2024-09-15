package com.pi.mesacompartilhada.repositories;

import com.pi.mesacompartilhada.models.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends MongoRepository<Empresa, String> {
    Optional<Empresa> findByEmail(String email);
}
