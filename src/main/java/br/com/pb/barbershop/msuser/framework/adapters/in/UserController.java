package br.com.pb.barbershop.msuser.framework.adapters.in;

import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final ModelMapper mapper;
    private final UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO obj) {
        return ResponseEntity.created
                (fromCurrentRequest().path("/{id}").buildAndExpand(service.create(obj).getId()).toUri()).build();

    }

    @PutMapping("{id}")
    public User update(@PathVariable Long id, @RequestBody @Valid UserDTO dto) {
        User user = new User(id, dto.getName(), dto.getEmail(), dto.getPhone(), dto.getDocument());
        return service.update(user);

    }


}




