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
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<User> findByName(String name, Pageable pageable){
        return repository.findByName(name, pageable);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }


}
