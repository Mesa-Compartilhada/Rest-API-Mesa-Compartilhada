package com.pi.mesacompartilhada.controllers;

import com.pi.mesacompartilhada.models.Endereco;
import com.pi.mesacompartilhada.records.request.EnderecoRequestDto;
import com.pi.mesacompartilhada.records.response.EnderecoResponseDto;
import com.pi.mesacompartilhada.services.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/apimc")
public class EnderecoController {
    private final EnderecoService enderecoService;

    @Autowired
    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping(path="/endereco", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EnderecoResponseDto>> getAllEnderecos() {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.getAllEnderecos());
    }

    @GetMapping(path="/endereco/{enderecoId}")
    public ResponseEntity<Object> getEnderecoById(@PathVariable(value="enderecoId") String enderecoId) {
        Map<String, String> message = new HashMap<>();
        Optional<EnderecoResponseDto> endereco = enderecoService.getEnderecoById(enderecoId);
        if(endereco.isEmpty()) {
            message.put("message", "Endereço não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @PostMapping(path="/endereco")
    public ResponseEntity<Object> addEndereco(@RequestBody @Valid EnderecoRequestDto enderecoRequestDto) {
        Map<String, String> message = new HashMap<>();
        Optional<EnderecoResponseDto> result = enderecoService.addEndereco(enderecoRequestDto);
        if(result.isEmpty()) {
            message.put("message", "Endereço inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path="/endereco/{enderecoId}")
    public ResponseEntity<Object> updateEndereco(@PathVariable(value="enderecoId") String enderecoId,
                                                 @RequestBody @Valid EnderecoRequestDto enderecoRequestDto) {
        Map<String, String> message = new HashMap<>();
        Optional<EnderecoResponseDto> result = enderecoService.updateEndereco(enderecoId, enderecoRequestDto);
        if(result.isEmpty()) {
            message.put("message", "Endereço não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping(path="/endereco/{enderecoId}")
    public ResponseEntity<Object> deleteEndereco(@PathVariable(value="enderecoId") String enderecoId){
        Map<String, String> message = new HashMap<>();
        Optional<EnderecoResponseDto> result = enderecoService.deleteEndereco(enderecoId);
        if(result.isEmpty()) {
            message.put("message", "Endereço não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        message.put("message", "Endereço com ID " + enderecoId + " deletado");
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
