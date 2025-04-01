package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.models.PasswordToken;
import com.pi.mesacompartilhada.producer.TokenProducer;
import com.pi.mesacompartilhada.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private TokenProducer tokenProducer;
    @Autowired
    private TokenRepository tokenRepository;

    public void sendToken(String email) {
        PasswordToken passwordToken = new PasswordToken(UUID.randomUUID(), true, email, LocalDateTime.now().plusMinutes(20));
        tokenRepository.save(passwordToken);
        tokenProducer.publishMessage(passwordToken);
    }

}
