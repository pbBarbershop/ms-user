package br.com.pb.barbershop.msuser.framework.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationException {

    private String field;
    private String errorMessage;
}
