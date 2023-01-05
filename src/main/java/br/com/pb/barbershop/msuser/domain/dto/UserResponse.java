package br.com.pb.barbershop.msuser.domain.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String description;
    private String profileName;
}
