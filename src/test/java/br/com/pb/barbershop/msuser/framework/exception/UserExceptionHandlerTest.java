package br.com.pb.barbershop.msuser.framework.exception;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserExceptionHandlerTest {

    @InjectMocks
    private UserExceptionHandler exceptionHandler;

    @Test
    void handleExceptionInternal() {
        Exception ex = new Exception("test exception");
        WebRequest request = mock(WebRequest.class);

        UserExceptionHandler handler = new UserExceptionHandler();
        ResponseEntity<Object> response = handler.handleExceptionInternal(
                ex,
                null,
                null,
                HttpStatus.BAD_REQUEST,
                request
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("test exception", errorResponse.getMessage());
    }

    @Test
    public void handle() {
        Exception ex1 = mock(Exception.class);
        GenericException ex2 = mock(GenericException.class);
        when(ex2.getMessageDTO()).thenReturn("mensagem teste");
        when(ex2.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);

        UserExceptionHandler handler = new UserExceptionHandler();
        ResponseEntity<Object> response = handler.handle(ex1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errorResponse.getMessage());

        response = handler.handle(ex2);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        errorResponse = (ErrorResponse) response.getBody();
        assertEquals("mensagem teste", errorResponse.getMessage());
    }

    @Test
    public void handleDefault() {
        UserExceptionHandler handler = new UserExceptionHandler();
        ResponseEntity<Object> response = handler.handleDefault();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errorResponse.getMessage());
    }

    @Test
    public void handleGenericException() {
        GenericException ex = mock(GenericException.class);
        when(ex.getMessageDTO()).thenReturn("mensagem teste");
        when(ex.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);

        UserExceptionHandler handler = new UserExceptionHandler();
        ResponseEntity<Object> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("mensagem teste", errorResponse.getMessage());
    }

}