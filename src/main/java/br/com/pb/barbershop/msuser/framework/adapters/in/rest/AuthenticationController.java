package br.com.pb.barbershop.msuser.framework.adapters.in.rest;

import br.com.pb.barbershop.msuser.domain.dto.LoginDTO;
import br.com.pb.barbershop.msuser.domain.dto.TokenJwtResponseDTO;
import br.com.pb.barbershop.msuser.domain.model.User;
import br.com.pb.barbershop.msuser.framework.config.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login")
@RestController
@RequestMapping("/auth/login")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager manager;
    private final TokenService tokenService;

    @Operation(summary = "Autenticar usuário")
    @PostMapping
    public ResponseEntity login(@RequestBody LoginDTO loginDTO){
        var authToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        var authentication = manager.authenticate(authToken);
        var tokenJwt = tokenService.buildToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenJwtResponseDTO(tokenJwt));
    }
}
