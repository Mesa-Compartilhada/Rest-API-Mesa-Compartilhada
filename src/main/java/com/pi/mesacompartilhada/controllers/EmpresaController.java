package com.pi.mesacompartilhada.controllers;

import com.pi.mesacompartilhada.models.Empresa;
import com.pi.mesacompartilhada.records.EmpresaRecordDto;
import com.pi.mesacompartilhada.services.EmpresaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<Empresa> getAllEmpresas(HttpServletResponse response) {
        return empresaService.getAllEmpresas();
    }

    @GetMapping(path="/empresa/{empresaId}")
    public ResponseEntity<Object> getEmpresaById(@PathVariable(value="empresaId") String empresaId) {
        Optional<Empresa> result = empresaService.getEmpresaById(empresaId);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(path="/empresa/email/{email}")
    public ResponseEntity<Object> getEmpresaByEmail(@PathVariable(value="email") String email) {
        Optional<Empresa> result = empresaService.getEmpresaByEmail(email);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping(path="/empresa")
    public ResponseEntity<Object> addEmpresa(@RequestBody @Valid EmpresaRecordDto empresaRecordDto) {
        Optional<Empresa> result = empresaService.addEmpresa(empresaRecordDto);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empresa inválida");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping(path="/empresa/{empresaId}")
    public ResponseEntity<Object> updateEmpresa(@PathVariable(value="empresaId") String empresaId,
                                                @RequestBody @Valid EmpresaRecordDto empresaRecordDto) {
        Optional<Empresa> result = empresaService.updateEmpresa(empresaId, empresaRecordDto);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping(path="/empresa/{empresaId}")
    public ResponseEntity<Object> deleteEmpresa(@PathVariable(value="empresaId") String empresaId) {
        Optional<Empresa> result = empresaService.deleteEmpresa(empresaId);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Empresa com ID " + empresaId + " deletada");
    }
}
