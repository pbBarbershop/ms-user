package br.com.pb.barbershop.msuser.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    public Long getId;
    private String name;
    private String email;
    private String phone;
    private String document;

}
