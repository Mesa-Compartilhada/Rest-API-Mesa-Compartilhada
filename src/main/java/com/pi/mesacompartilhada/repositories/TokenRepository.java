package com.pi.mesacompartilhada.repositories;

import com.pi.mesacompartilhada.models.PasswordToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<PasswordToken, String> {
}
