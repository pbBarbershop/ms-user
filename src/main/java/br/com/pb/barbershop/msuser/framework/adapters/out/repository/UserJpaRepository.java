package br.com.pb.barbershop.msuser.framework.adapters.out.repository;

import br.com.pb.barbershop.msuser.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Page<User> findByName(String name, Pageable pageable);
}
