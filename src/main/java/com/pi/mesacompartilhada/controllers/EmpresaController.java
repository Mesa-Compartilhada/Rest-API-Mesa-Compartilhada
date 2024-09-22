package com.pi.mesacompartilhada.controllers;

import com.pi.mesacompartilhada.models.Empresa;
import com.pi.mesacompartilhada.records.request.EmpresaRequestDto;
import com.pi.mesacompartilhada.records.response.EmpresaResponseDto;
import com.pi.mesacompartilhada.services.EmpresaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Optional<EmpresaResponseDto> result = empresaService.getEmpresaById(empresaId);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(path="/empresa/email/{email}")
    public ResponseEntity<Object> getEmpresaByEmail(@PathVariable(value="email") String email) {
        Optional<EmpresaResponseDto> result = empresaService.getEmpresaByEmail(email);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping(path="/empresa")
    public ResponseEntity<Object> addEmpresa(@RequestBody @Valid EmpresaRequestDto empresaRequestDto) {
        Optional<EmpresaResponseDto> result = empresaService.addEmpresa(empresaRequestDto);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empresa inválida");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping(path="/empresa/{empresaId}")
    public ResponseEntity<Object> updateEmpresa(@PathVariable(value="empresaId") String empresaId,
                                                @RequestBody @Valid EmpresaRequestDto empresaRequestDto) {
        Optional<EmpresaResponseDto> result = empresaService.updateEmpresa(empresaId, empresaRequestDto);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping(path="/empresa/{empresaId}")
    public ResponseEntity<Object> deleteEmpresa(@PathVariable(value="empresaId") String empresaId) {
        Optional<EmpresaResponseDto> result = empresaService.deleteEmpresa(empresaId);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Empresa com ID " + empresaId + " deletada");
    }

}
