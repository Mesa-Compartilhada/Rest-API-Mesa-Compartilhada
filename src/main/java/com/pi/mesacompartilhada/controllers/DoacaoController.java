package com.pi.mesacompartilhada.controllers;

import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.records.DoacaoRecordDto;
import com.pi.mesacompartilhada.services.DoacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<Doacao>> getAllDoacao() {
        return ResponseEntity.status(HttpStatus.OK).body(doacaoService.getAllDoacoes());
    }

    @GetMapping(path="/doacao/{doacaoId}")
    public ResponseEntity<Object> getDoacaoById(@PathVariable(value="doacaoId") String doacaoId) {
        Optional<Doacao> doacao = doacaoService.getDoacaoById(doacaoId);
        if(doacao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doação não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(doacao);
    }

    @PostMapping(path="/doacao")
    public ResponseEntity<Object> addDoacao(@RequestBody @Valid DoacaoRecordDto doacaoRecordDto){
        Optional<Doacao> result = doacaoService.addDoacao(doacaoRecordDto);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Doação inválida");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
