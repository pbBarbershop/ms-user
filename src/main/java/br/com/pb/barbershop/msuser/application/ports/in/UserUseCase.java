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
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import br.com.pb.barbershop.msuser.application.ports.out.ProfileRepository;
import br.com.pb.barbershop.msuser.domain.dto.UserResponse;
import br.com.pb.barbershop.msuser.domain.model.Profile;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserUseCase implements UserService {
    private final ModelMapper mapper;
    private final UserRepository repository;
    private final ProfileRepository profileRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserDTO obj) {
        findByEmail(obj);
        var user = new User();
        user.setName(obj.getName());
        user.setPhone(obj.getPhone());
        user.setEmail(obj.getEmail());
        user.setDocument(obj.getDocument());
        user.setPassword(passwordEncoder.encode(obj.getPassword()));
        String profileName = "ROLE_"+obj.getProfileName().toUpperCase();
        Optional<Profile> profile = profileRepository.findByNome(profileName);
        user.addProfile(profile.get());
        repository.save(user);
        var response = mapper.map(user, UserResponse.class);
        response.setProfileName(obj.getProfileName());
        return response;
    }
    @Override
    public UserResponse update(UserDTO obj, Long id) {
        obj.setId(id);
        findByEmail(obj);
        var userOp = repository.findById(id);
        if(!userOp.isPresent()){
            throw new ObjectNotFoundException("usuário não encontrado");
        }
        var user = userOp.get();
        user.setName(obj.getName());
        user.setPhone(obj.getPhone());
        user.setEmail(obj.getEmail());
        user.setDocument(obj.getDocument());
        user.setPassword(passwordEncoder.encode(obj.getPassword()));
        String profileName = "ROLE_"+obj.getProfileName().toUpperCase();
        Profile profileToRemove = user.getProfile().get(0);
        user.removeProfile(profileToRemove);
        Profile profile = profileRepository.findByNome(profileName).get();
        user.addProfile(profile);
        repository.save(user);
        var response = mapper.map(user, UserResponse.class);
        response.setProfileName(obj.getProfileName());
        return response;
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
