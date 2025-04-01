package com.pi.mesacompartilhada.repositories;

import com.pi.mesacompartilhada.models.PasswordToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<PasswordToken, String> {

    Optional<PasswordToken> findByToken(String token);

}
