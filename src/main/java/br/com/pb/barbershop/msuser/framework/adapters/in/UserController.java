package br.com.pb.barbershop.msuser.framework.adapters.in;

import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    private final ModelMapper mapper;
    private final UserService service;

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO obj) {
        obj.setId();
        return ResponseEntity.ok().body(mapper.map(service.update(obj), UserDTO.class));
    }


}




