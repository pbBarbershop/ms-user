package br.com.pb.barbershop.msuser.application.ports.out;

import br.com.pb.barbershop.msuser.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepositoryPortOut {
    Optional<User> findByEmail(String email);
    Page<User> findByName(String name, Pageable pageable);
}
