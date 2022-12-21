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
import org.springframework.http.HttpStatus;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserId(@PathVariable Long id) {
        service.deleteUserId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public PageableDTO findAll(@RequestParam(required = false) String name, Pageable pageable) {
        return this.service.findAll(name, pageable);
    }
}

