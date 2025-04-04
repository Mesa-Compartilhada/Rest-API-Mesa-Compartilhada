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

    public void enviarToken(String email) {
        PasswordToken passwordToken = new PasswordToken(UUID.randomUUID().toString(), true, email, LocalDateTime.now().plusMinutes(20));
        tokenRepository.save(passwordToken);
        tokenProducer.publishMessage(passwordToken);
    }

    public boolean verificarToken(String token) {
        var t = tokenRepository.findByToken(token);
        if(t.isPresent()) {
            boolean result = t.get().isStatus() && t.get().getExp().isAfter(LocalDateTime.now());
            if(t.get().isStatus()) {
                t.get().setStatus(false);
                tokenRepository.save(t.get());
            }
            return result;
        }
        else {
            return false;
        }
    }

}
