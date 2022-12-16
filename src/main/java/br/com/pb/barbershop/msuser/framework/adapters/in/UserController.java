package br.com.pb.barbershop.msuser.framework.adapters.in;

import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO obj) {
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").buildAndExpand(service.create(obj).getId()).toUri()).build();

    }

    @PutMapping(value = ID)
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO obj) {
        obj.setId(id);
        return ResponseEntity.ok().body(mapper.map(service.update(obj), UserDTO.class));
    }


}




