package br.com.pb.barbershop.msuser.framework.adapters.out.repository;

import br.com.pb.barbershop.msuser.application.ports.out.UserRepositoryPortOut;
import br.com.pb.barbershop.msuser.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryPortOut {
    private final UserJpaRepository repository;
    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Page<User> findByName(String name, Pageable pageable){
        return repository.findByName(name, pageable);
    }
}
