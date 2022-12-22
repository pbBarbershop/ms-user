package br.com.pb.barbershop.msuser.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseGetAll {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String document;
}
