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
        Optional<EnderecoResponseDto> endereco = enderecoService.getEnderecoById(enderecoId);
        if(endereco.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @PostMapping(path="/endereco")
    public ResponseEntity<Object> addEndereco(@RequestBody @Valid EnderecoRequestDto enderecoRequestDto) {
        Optional<EnderecoResponseDto> result = enderecoService.addEndereco(enderecoRequestDto);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endereço inválido");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(path="/endereco/{enderecoId}")
    public ResponseEntity<Object> updateEndereco(@PathVariable(value="enderecoId") String enderecoId,
                                                 @RequestBody @Valid EnderecoRequestDto enderecoRequestDto) {
        Optional<EnderecoResponseDto> result = enderecoService.updateEndereco(enderecoId, enderecoRequestDto);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping(path="/endereco/{enderecoId}")
    public ResponseEntity<Object> deleteEndereco(@PathVariable(value="enderecoId") String enderecoId){
        Optional<EnderecoResponseDto> result = enderecoService.deleteEndereco(enderecoId);
        if(result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Endereço com ID " + enderecoId + " deletado");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
