package br.com.pb.barbershop.msuser.application.ports.out;

import br.com.pb.barbershop.msuser.domain.model.Profile;

import java.util.Optional;

public interface ProfileRepositoryPortOut {
    Optional<Profile> findByName(String name);
}
