package br.com.pb.barbershop.msuser.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank
    @Pattern(regexp = "^([a-zA-ZãÃéÉíÍóÓêÊôÔáÁ\s])+$", message =
            "O campo nome deve possuir apenas letras e não pode estar em branco!")
    private String name;
    @Email
    private String email;
    @NotBlank
    @Length(min = 11, max = 11, message =
            "Deve conter ddd + número (11 caracteres ex. 11111111111).")
    @Pattern(regexp = "^([0-9])+$", message =
            "Deve conter apenas números, ddd + número (11 caracteres ex. 11111111111)")
    private String phone;

    private String description;
    @Length(min = 8, message = "Sua senha deve ter no minimo 8 digitos!")
    @NotBlank
    private String password;
    @NotBlank
    @Pattern(regexp = "^(?i)(Manager|Customer|Employee)$")
    private String profileName;

}

