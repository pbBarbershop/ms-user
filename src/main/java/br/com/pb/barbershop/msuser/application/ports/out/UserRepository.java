package br.com.pb.barbershop.msuser.application.ports.out;

import br.com.pb.barbershop.msuser.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);



}
