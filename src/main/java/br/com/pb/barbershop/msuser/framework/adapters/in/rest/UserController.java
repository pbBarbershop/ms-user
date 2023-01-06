package br.com.pb.barbershop.msuser.framework.adapters.in.rest;

import br.com.pb.barbershop.msuser.application.ports.in.UserUseCase;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "User")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserUseCase service;

    @Operation(summary = "Cadastrar usuário")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserDTO obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(obj));
    }

    @Operation(summary = "Atualizar usuário")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Valid UserDTO obj) {
        return ResponseEntity.ok().body(service.update(obj, id));
    }

    @Operation(summary = "Excluir usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserId(@PathVariable Long id) {
        service.deleteUserId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Listar usuários")
    @GetMapping
    public PageableDTO findAll(@RequestParam(required = false) String name, Pageable pageable) {
        return this.service.findAll(name, pageable);
    }

    @Operation(summary = "Buscar usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse>findById(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findById(id));

    }
}




