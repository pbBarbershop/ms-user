package br.com.pb.barbershop.msuser.framework.adapters.out.repository;


import br.com.pb.barbershop.msuser.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByName(String nome);
}
