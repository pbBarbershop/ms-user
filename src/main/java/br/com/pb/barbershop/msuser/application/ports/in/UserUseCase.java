package br.com.pb.barbershop.msuser.application.ports.in;

import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserUseCase implements UserService {

    private final UserRepository repository;

    @Override
    public List<User> findAll() {
        return this.repository.findAll();
    }
}
