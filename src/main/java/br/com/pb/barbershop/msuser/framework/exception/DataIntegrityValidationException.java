package br.com.pb.barbershop.msuser.framework.exception;

public class DataIntegrityValidationException extends RuntimeException {

    public DataIntegrityValidationException(String message) {
        super(message);
    }
}