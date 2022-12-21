package br.com.pb.barbershop.msuser.framework.adapters.in;
import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.pb.barbershop.msuser.domain.dto.UserResponse;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final ModelMapper mapper;
    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserDTO obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(obj));
    }

    @PutMapping()
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Valid UserDTO obj) {
        return ResponseEntity.ok().body(service.update(obj, id));
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

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO>findById(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findById(id));

    }
}




