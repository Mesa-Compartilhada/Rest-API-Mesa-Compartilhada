package com.pi.mesacompartilhada.controllers;

import com.pi.mesacompartilhada.records.request.EmpresaLoginRequestDto;
import com.pi.mesacompartilhada.records.request.EmpresaRequestDto;
import com.pi.mesacompartilhada.records.response.EmpresaResponseDto;
import com.pi.mesacompartilhada.services.EmpresaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(path="/empresa")
    public ResponseEntity<Object> addEmpresa(@RequestBody @Valid EmpresaRequestDto empresaRequestDto) {
        Map<String, String> message = new HashMap<>();
        Optional<EmpresaResponseDto> result = empresaService.addEmpresa(empresaRequestDto);
        if(result.isEmpty()) {
            message.put("message", "Empresa inválida");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping(path="/empresa/{empresaId}")
    public ResponseEntity<Object> updateEmpresa(@PathVariable(value="empresaId") String empresaId,
                                                @RequestBody @Valid EmpresaRequestDto empresaRequestDto) {
        Map<String, String> message = new HashMap<>();
        Optional<EmpresaResponseDto> result = empresaService.updateEmpresa(empresaId, empresaRequestDto);
        if(result.isEmpty()) {
            message.put("message", "Empresa não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping(path="/empresa/{empresaId}")
    public ResponseEntity<Object> deleteEmpresa(@PathVariable(value="empresaId") String empresaId) {
        Map<String, String> message = new HashMap<>();
        Optional<EmpresaResponseDto> result = empresaService.deleteEmpresa(empresaId);
        if(result.isEmpty()) {
            message.put("message", "Empresa não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        message.put("message", "Empresa com ID " + empresaId + " deletada");
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping(path="/empresa/login")
    public ResponseEntity<Object> login(@RequestBody @Valid EmpresaLoginRequestDto empresaLoginRequestDto) {
        Map<String, String> message = new HashMap<>();
        Optional<EmpresaResponseDto> result = empresaService.login(empresaLoginRequestDto);
        if(result.isEmpty()) {
            message.put("message", "Dados inválidos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
