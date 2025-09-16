package com.pi.mesacompartilhada.controllers;

import com.pi.mesacompartilhada.models.UserPrincipal;
import com.pi.mesacompartilhada.records.empresa.*;
import com.pi.mesacompartilhada.services.EmpresaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("apimc")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping(path="/empresa/login")
    public ResponseEntity<Object> login(@RequestBody @Valid EmpresaLoginRequestDto empresaLoginRequestDto) {
        Map<String, String> message = new HashMap<>();
        Optional<String> result = empresaService.login(empresaLoginRequestDto);
        if(result.isEmpty()) {
            message.put("message", "Credenciais inválidas");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        message.put("token", result.get());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping(path="/empresa/register")
    public ResponseEntity<Object> addEmpresa(@RequestBody @Valid EmpresaRequestDto empresaRequestDto) {
        Map<String, String> message = new HashMap<>();
        Optional<EmpresaResponseDto> result = empresaService.addEmpresa(empresaRequestDto);
        if(result.isEmpty()) {
            message.put("message", "Empresa inválida");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(path="/empresa/me")
    public ResponseEntity<Object> getEmpresaAutenticada(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        HttpStatus responseStatus = null;
        Map<String, Object> responseBody = new HashMap<>();
        Optional<EmpresaResponseDto> user = empresaService.getEmpresaById(userPrincipal.getId());
        if(user.isEmpty()) {
            responseStatus = HttpStatus.NOT_FOUND;
            responseBody.put("message", "Usuário não encontrado");
        }
        else {
            responseStatus = HttpStatus.OK;
            responseBody.put("user", user);
        }
        return ResponseEntity.status(responseStatus).body(responseBody);
    }

    @GetMapping(path="/empresa", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmpresaResponseDto> getAllEmpresas(HttpServletResponse response) {
        return empresaService.getAllEmpresas();
    }

    @GetMapping(path="/empresa/{empresaId}")
    public ResponseEntity<Object> getEmpresaById(@PathVariable(value="empresaId") String empresaId) {
        Map<String, String> message = new HashMap<>();
        Optional<EmpresaResponseDto> result = empresaService.getEmpresaById(empresaId);
        if(result.isEmpty()) {
            message.put("message", "Empresa não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(path="/empresa/email/{email}")
    public ResponseEntity<Object> getEmpresaByEmail(@PathVariable(value="email") String email) {
        Map<String, String> message = new HashMap<>();
        Optional<EmpresaResponseDto> result = empresaService.getEmpresaByEmail(email);
        if(result.isEmpty()) {
            message.put("message", "Empresa não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping(path="/empresa")
    public ResponseEntity<Object> updateEmpresa(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @RequestBody @Valid EmpresaUpdateDto empresaUpdateDto) {
        Map<String, String> message = new HashMap<>();
        Optional<EmpresaResponseDto> result = empresaService.updateEmpresa(userPrincipal.getId(), empresaUpdateDto);
        if(result.isEmpty()) {
            message.put("message", "Empresa não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping(path="/empresa")
    public ResponseEntity<Object> deleteEmpresa(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Map<String, String> message = new HashMap<>();
        Optional<EmpresaResponseDto> result = empresaService.deleteEmpresa(userPrincipal.getId());
        if(result.isEmpty()) {
            message.put("message", "Empresa não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        message.put("message", "Empresa com ID " + userPrincipal.getId() + " deletada");
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    // recebe novas senhas e o token de recuperacao de senha
    @PostMapping(path="/empresa/recuperar-senha")
    public ResponseEntity<Object> resetPassword(@RequestBody @Valid EmpresaResetPasswordDto empresaResetPasswordDto) {
        HttpStatus statusCode = HttpStatus.OK;
        Map<String, String> message = new HashMap<>();
        boolean result = empresaService.resetPassword(empresaResetPasswordDto);
        if(result) {
            message.put("message", "Senha atualizada");
        }
        else {
            statusCode = HttpStatus.NOT_FOUND;
            message.put("message", "Token ou empresa inválidos");
        }
        return ResponseEntity.status(statusCode).body(message);
    }

    @PutMapping(path="/empresa/atualizar-senha")
    public ResponseEntity<Object> updatePassword(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid EmpresaUpdatePasswordDto empresaUpdatePasswordDto) {
        HttpStatus responseStatus = null;
        Map<String, Object> responseBody = new HashMap<>();
        boolean result = empresaService.updatePassword(userPrincipal.getId(), empresaUpdatePasswordDto.senhaAtual(), empresaUpdatePasswordDto.senhaNova());
        if(!result) {
            responseStatus = HttpStatus.UNAUTHORIZED;
            responseBody.put("message", "Credenciais inválidas");
        }
        else {
            responseStatus = HttpStatus.OK;
            responseBody.put("message", "Senha atualizada com sucesso");
        }
        return ResponseEntity.status(responseStatus).body(responseBody);
    }
}
