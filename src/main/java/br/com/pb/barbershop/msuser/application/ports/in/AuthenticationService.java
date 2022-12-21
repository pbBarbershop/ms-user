package br.com.pb.barbershop.msuser.application.ports.in;

import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = modelMapper.map(userRepository.findByEmail(username).get(), UserDetails.class);
        return modelMapper.map(userRepository.findByEmail(username).get(), UserDetails.class);
    }
}
