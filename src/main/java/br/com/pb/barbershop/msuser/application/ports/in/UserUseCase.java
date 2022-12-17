package br.com.pb.barbershop.msuser.application.ports.in;

import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.model.User;
import br.com.pb.barbershop.msuser.framework.exception.DataIntegrityValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserUseCase implements UserService {

    private final ModelMapper mapper;
    private final UserRepository repository;

    public UserUseCase(ModelMapper mapper, UserRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public User create(UserDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, User.class));
    }

    @Override
    public User update(UserDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, User.class));
    }

    private void findByEmail(UserDTO obj) {
        Optional<User> user = repository.findByEmail(obj.getEmail());
        if (user.isPresent() && !user.get().getId().equals(obj.getId())) {
            throw new DataIntegrityValidationException("Email ja registrado no sistema");
        }
    }
}




