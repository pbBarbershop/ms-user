package br.com.pb.barbershop.msuser.framework.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {
        var errorResponse = ErrorResponse.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        String badRequestMessage;
        if (
                ex.getBindingResult().getFieldError() != null &&
                        ex.getBindingResult().getFieldError().getDefaultMessage() != null
        ) {
            badRequestMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        } else {
            badRequestMessage = HttpStatus.BAD_REQUEST.getReasonPhrase();
        }
        var error = ErrorResponse.builder().message(badRequestMessage).build();
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handle(Exception ex) {
        if (ex instanceof GenericException) {
            return handleGenericException(((GenericException) ex));
        }
        return handleDefault();
    }

    public ResponseEntity<Object> handleDefault() {
        var errorResponse = ErrorResponse.builder().message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> handleGenericException(GenericException ex) {
        var errorResponse = ErrorResponse.builder().message(ex.getMessageDTO()).build();
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        var errorResponse = ErrorResponse.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Object> handleBadCredentialsException(InternalAuthenticationServiceException ex) {
        var errorResponse = ErrorResponse.builder().message("Usuário ou senha inválida").build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
