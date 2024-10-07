package com.pi.mesacompartilhada.controllers;

import com.pi.mesacompartilhada.exception.DoacaoStatusIllegalArgumentException;
import com.pi.mesacompartilhada.exception.DoacaoStatusOperationNotSupportedException;
import com.pi.mesacompartilhada.records.request.DoacaoRequestDto;
import com.pi.mesacompartilhada.records.request.DoacaoStateRequestDto;
import com.pi.mesacompartilhada.records.response.DoacaoResponseDto;
import com.pi.mesacompartilhada.services.DoacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/apimc")
public class DoacaoController {
    private final DoacaoService doacaoService;

    @Autowired
    public DoacaoController(DoacaoService doacaoService) {
        this.doacaoService = doacaoService;
    }

    @GetMapping(path="/doacao", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DoacaoResponseDto>> getAllDoacoes() {
        return ResponseEntity.status(HttpStatus.OK).body(doacaoService.getAllDoacoes());
    }

    @GetMapping(path="/doacao/{doacaoId}")
    public ResponseEntity<Object> getDoacaoById(@PathVariable(value="doacaoId") String doacaoId) {
        Map<String, String> message = new HashMap<>();
        Optional<DoacaoResponseDto> doacao = doacaoService.getDoacaoById(doacaoId);
        if(doacao.isEmpty()) {
            message.put("message", "Doação não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(doacao);
    }

    @GetMapping(path="/doacao/empresa-doadora/{empresaDoadoraId}")
    public ResponseEntity<Object> getDoacoesByEmpresaDoadoraId(@PathVariable(value="empresaDoadoraId") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(doacaoService.getDoacoesByEmpresaDoadoraId(id));
    }

    @GetMapping(path="/doacao/empresa-recebedora/{empresaRecebedoraId}")
    public ResponseEntity<Object> getDoacoesByEmpresaRecebedoraId(@PathVariable(value="empresaRecebedoraId") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(doacaoService.getDoacoesByEmpresaRecebedoraId(id));
    }

    @GetMapping(path="/doacao/status/{status}")
    public ResponseEntity<Object> getDoacoesByStatus(@PathVariable(value="status") String status) {
        return ResponseEntity.status(HttpStatus.OK).body(doacaoService.getDoacoesByStatus(status));
    }

    @PostMapping(path="/doacao")
    public ResponseEntity<Object> addDoacao(@RequestBody @Valid DoacaoRequestDto doacaoRequestDto){
        Map<String, String> message = new HashMap<>();
        Optional<DoacaoResponseDto> result = doacaoService.addDoacao(doacaoRequestDto);
        if(result.isEmpty()) {
            message.put("message", "Doação inválida");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path="/doacao/{doacaoId}")
    public ResponseEntity<Object> updateDoacao(@PathVariable(value="doacaoId") String doacaoId,
                                               @RequestBody @Valid DoacaoRequestDto doacaoRequestDto) {
        Map<String, String> message = new HashMap<>();
        Optional<DoacaoResponseDto> doacao = doacaoService.updateDoacao(doacaoId, doacaoRequestDto);
        if(doacao.isEmpty()) {
            message.put("message", "Doação não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(doacao);
    }

    @PutMapping(path="/doacao/status/{doacaoId}")
    public ResponseEntity<Object> updateDoacaoState(@PathVariable(value="doacaoId") String doacaoId,
                                                    @RequestBody @Valid DoacaoStateRequestDto doacaoStateRequestDto) throws DoacaoStatusOperationNotSupportedException, DoacaoStatusIllegalArgumentException {
        Map<String, String> message = new HashMap<>();
        Optional<DoacaoResponseDto> doacao = doacaoService.updateDoacaoState(doacaoId, doacaoStateRequestDto);
        if(doacao.isEmpty()) {
            message.put("message", "Doação não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(doacao);
    }

    @DeleteMapping(path="/doacao/{doacaoId}")
    public ResponseEntity<Object> deleteDoacao(@PathVariable(value="doacaoId") String doacaoId) {
        Map<String, String> message = new HashMap<>();
        Optional<DoacaoResponseDto> doacao = doacaoService.deleteDoacao(doacaoId);
        if(doacao.isEmpty()) {
            message.put("message", "Doação não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        message.put("message", "Doacao com ID " + doacaoId + " deletada");
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
