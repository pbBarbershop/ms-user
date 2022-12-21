package br.com.pb.barbershop.msuser.application.ports.in;

import br.com.pb.barbershop.msuser.application.ports.out.ProfileRepository;
import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserResponse;
import br.com.pb.barbershop.msuser.domain.model.Profile;
import br.com.pb.barbershop.msuser.domain.model.User;
import br.com.pb.barbershop.msuser.framework.exception.DataIntegrityValidationException;
import br.com.pb.barbershop.msuser.framework.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
       // var user = repository.findById(id).get();
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
}




