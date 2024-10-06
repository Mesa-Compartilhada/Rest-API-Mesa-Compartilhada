package com.pi.mesacompartilhada.exception;

import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    // Quando o state atual da doação não suportar a operação
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DoacaoStateOperationNotSupportedException.class)
    public ResponseEntity<Object> handleDoacaoStateOperationNotSupportedException(DoacaoStateOperationNotSupportedException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error: ",ex.getMessage());
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    // Quando o state atual suporta a operação, porém não foi enviado o id da empresa recebedora
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error: ",ex.getMessage());
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    // Quando um campo
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<Object> handleMongoWriteException(MongoWriteException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error: ",ex.getMessage());
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

}
