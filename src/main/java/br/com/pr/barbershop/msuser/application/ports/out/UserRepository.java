package br.com.pr.barbershop.msuser.application.ports.out;

import br.com.pr.barbershop.msuser.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
