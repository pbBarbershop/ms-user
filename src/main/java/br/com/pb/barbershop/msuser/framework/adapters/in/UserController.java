package br.com.pb.barbershop.msuser.framework.adapters.in;

import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String ID = "/{id}";

    private final ModelMapper mapper;
    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserDTO obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(obj));
    }

    @PutMapping(value = ID)
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Valid UserDTO obj) {
        return ResponseEntity.ok().body(service.update(obj, id));
    }


}




