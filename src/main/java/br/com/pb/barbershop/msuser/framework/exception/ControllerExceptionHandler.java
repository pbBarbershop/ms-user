package br.com.pb.barbershop.msuser.framework.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
        StandardError error = new StandardError
                (LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<StandardError> idNotFound(IdNotFoundException ex, HttpServletRequest request) {
        StandardError error = new StandardError
                (LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(DataIntegrityValidationException.class)
    public ResponseEntity<StandardError> dataIntegratyViolationException
            (DataIntegrityValidationException ex, HttpServletRequest request) {
        StandardError error = new StandardError
                (LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}