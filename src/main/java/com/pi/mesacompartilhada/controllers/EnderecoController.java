package com.pi.mesacompartilhada.controllers;

import com.pi.mesacompartilhada.models.Endereco;
import com.pi.mesacompartilhada.records.EnderecoRecordDto;
import com.pi.mesacompartilhada.services.EnderecoService;
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
public class EnderecoController {
    private final EnderecoService enderecoService;

    @Autowired
    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping(path="/endereco", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Endereco>> getAllEnderecos() {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.getAllEnderecos());
    }

    @GetMapping(path="/endereco/{enderecoId}")
    public ResponseEntity<Object> getEnderecoById(@PathVariable(value="enderecoId") String enderecoId) {
        Optional<Endereco> endereco = enderecoService.getEnderecoById(enderecoId);
        if(endereco.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @PostMapping(path="/endereco")
    public ResponseEntity<Object> addEndereco(@RequestBody @Valid EnderecoRecordDto enderecoRecordDto) {
        Optional<Endereco> result = enderecoService.addEndereco(enderecoRecordDto);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endereço inválido");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path="/endereco/{enderecoId}")
    public ResponseEntity<Object> updateEndereco(@PathVariable(value="enderecoId") String enderecoId,
                                                 @RequestBody @Valid EnderecoRecordDto enderecoRecordDto) {
        Optional<Endereco> result = enderecoService.updateEndereco(enderecoId, enderecoRecordDto);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping(path="/endereco/{enderecoId}")
    public ResponseEntity<Object> deleteEndereco(@PathVariable(value="enderecoId") String enderecoId){
        Optional<Endereco> result = enderecoService.deleteEndereco(enderecoId);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Endereço com ID " + enderecoId + " deletado");
    }
}
