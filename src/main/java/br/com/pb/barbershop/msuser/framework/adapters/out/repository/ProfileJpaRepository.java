package br.com.pb.barbershop.msuser.framework.adapters.out.repository;


import br.com.pb.barbershop.msuser.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileJpaRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByName(String nome);
}
