package br.com.pb.barbershop.msuser.framework.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SecurityException {
    private Integer status;
    private String message;
}
