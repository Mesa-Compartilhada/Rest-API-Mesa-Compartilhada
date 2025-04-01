package com.pi.mesacompartilhada.controllers;

import com.pi.mesacompartilhada.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("apimc")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token/{email}")
    public void sendToken(@PathVariable(value="email") String email) {
        tokenService.sendToken(email);
    }

}
