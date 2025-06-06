package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.models.PasswordToken;
import com.pi.mesacompartilhada.producer.TokenProducer;
import com.pi.mesacompartilhada.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class TokenService {

    @Autowired
    private TokenProducer tokenProducer;
    @Autowired
    private TokenRepository tokenRepository;

    public boolean enviarToken(String email) {
        try {
            var random = new Random();
            PasswordToken passwordToken = new PasswordToken(String.valueOf(100000 + random.nextInt(900000)), true, email, LocalDateTime.now().plusMinutes(20));
            tokenRepository.save(passwordToken);
            tokenProducer.publishMessage(passwordToken);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public boolean verificarToken(String token) {
        var t = tokenRepository.findByToken(token);
        if(t.isPresent()) {
            boolean result = t.get().isStatus() && t.get().getExp().isAfter(LocalDateTime.now());
            return result;
        }
        else {
            return false;
        }
    }

    public Optional<PasswordToken> getToken(String token) {
        Optional<PasswordToken> passwordToken = tokenRepository.findByToken(token);
        return passwordToken;
    }

    public void invalidarToken(PasswordToken token) {
        if(token.isStatus()) {
            token.setStatus(false);
            tokenRepository.save(token);
        }
    }

}
