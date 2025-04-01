package com.pi.mesacompartilhada.controllers;

import com.pi.mesacompartilhada.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("apimc")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token/{email}")
    public void enviarToken(@PathVariable(value="email") String email) {
        tokenService.enviarToken(email);
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<Object> verificarToken(@PathVariable(value="token") String token) {
        Map<String, String> message = new HashMap<>();
        boolean result = tokenService.verificarToken(token);
        if(!result) {
            message.put("message", "Token expirado ou inválido");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Token válido");
    }

}
