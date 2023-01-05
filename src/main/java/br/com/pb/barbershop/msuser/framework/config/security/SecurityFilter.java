package br.com.pb.barbershop.msuser.framework.config.security;

import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter  extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    private final ModelMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJwt = recuperarToken(request);
        if (tokenJwt != null){
            var subject = tokenService.getSubject(tokenJwt);
            var user = mapper.map(userRepository.findByEmail(subject), UserDetails.class);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorization  =request.getHeader("Authorization");
        if(authorization != null){
            return authorization.replace("Bearer ", "");
        }
        return null;
    }


}
