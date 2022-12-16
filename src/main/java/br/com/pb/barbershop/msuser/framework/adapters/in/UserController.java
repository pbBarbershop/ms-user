package br.com.pb.barbershop.msuser.framework.adapters.in;
import br.com.pb.barbershop.msuser.application.ports.in.UserUseCase;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserUseCase userCase;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO>findById(@PathVariable Long id){
        return ResponseEntity.ok().body(userCase.findById(id));
    }
}

