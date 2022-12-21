package br.com.pb.barbershop.msuser.application.ports.in;

import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.model.User;
import br.com.pb.barbershop.msuser.framework.exception.DataIntegrityValidationException;
import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import br.com.pb.barbershop.msuser.framework.exception.IdNotFoundException;
import br.com.pb.barbershop.msuser.framework.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
@Service
@AllArgsConstructor
public class UserUseCase implements UserService {
    private final ModelMapper mapper;
    private final UserRepository repository;

    @Override
    public User create(UserDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, User.class));
    }

    @Override
    public User update(User user) {
        if(!repository.existsById(user.getId()))
            throw new NoResultException(String.format("User nao encontrado", user.getId()));
        return repository.save(user);
    }

    private void findByEmail(UserDTO obj) {
        Optional<User> user = repository.findByEmail(obj.getEmail());
        if (user.isPresent() && !user.get().getId().equals(obj.getId())) {
            throw new DataIntegrityValidationException("Email ja registrado no sistema");
        }
    }

    private void checkIfIdExists(Long id){
        repository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }
    @Override
    public void deleteUserId(Long id) {
        checkIfIdExists(id);
        repository.deleteById(id);
    }
    @Override
    public PageableDTO findAll(String name, Pageable pageable) {
        Page<User> page = name == null ?
                repository.findAll(pageable) : repository.findByName(name, pageable);
        List<User> users = page.getContent();
        List<UserDTO> usersDTO = mapper.map(users, new TypeToken<List<UserDTO>>() {
        }.getType());
        return PageableDTO.builder().numberOfElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages()).usersDTO(usersDTO).build();

    }
    @Override
    public UserDTO findById(Long id){
        return mapper.map(getUser(id), UserDTO.class );
    }

    public User getUser(Long id){
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("usuário não encontrado!"));

    }
}

