package br.com.pb.barbershop.msuser.framework.adapters.out.repository;

import br.com.pb.barbershop.msuser.application.ports.out.ProfileRepositoryPortOut;
import br.com.pb.barbershop.msuser.domain.model.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class ProfileRepositoryImpl implements ProfileRepositoryPortOut {

    private final ProfileJpaRepository repository;
    @Override
    public Optional<Profile> findByName(String name) {
        return repository.findByName(name);
    }
}
