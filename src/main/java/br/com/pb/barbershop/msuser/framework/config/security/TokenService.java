package br.com.pb.barbershop.msuser.framework.config.security;

import br.com.pb.barbershop.msuser.domain.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${token.secret}")
    private String secret;
    public String buildToken(User user){
        try{
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("ms-user")
                    .withSubject(user.getEmail())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        }catch(JWTCreationException exception){
            throw new RuntimeException("erro ao gerar token jwt", exception);
        }
    }

    public String getSubject(String tokenJwt){
        try{
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ms-user")
                    .build()
                    .verify(tokenJwt)
                    .getSubject();
        }catch(JWTVerificationException exception){
            throw new RuntimeException("Token inválido");
        }
    }

    private Instant expirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
