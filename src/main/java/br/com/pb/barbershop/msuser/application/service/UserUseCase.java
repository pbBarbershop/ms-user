package br.com.pb.barbershop.msuser.application.service;
import br.com.pb.barbershop.msuser.application.ports.in.UserService;
import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserResponseGetAll;
import br.com.pb.barbershop.msuser.domain.model.User;
import br.com.pb.barbershop.msuser.framework.exception.DataIntegrityValidationException;
import org.modelmapper.ModelMapper;
import br.com.pb.barbershop.msuser.framework.exception.IdNotFoundException;
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

@Service
@RequiredArgsConstructor
public class UserUseCase implements UserService {
    private final ModelMapper mapper;
    private final UserRepository repository;
    private final ProfileRepository profileRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserDTO userDTO) {
        findByEmail(userDTO);
        verifyIfDocumentExists(userDTO);
        var user = new User();
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setDocument(userDTO.getDocument());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        String profileName = "ROLE_"+userDTO.getProfileName().toUpperCase();
        Optional<Profile> profile = profileRepository.findByName(profileName);
        user.addProfile(profile.get());
        repository.save(user);
        var response = mapper.map(user, UserResponse.class);
        response.setProfileName(userDTO.getProfileName());
        return response;
    }
    @Override
    public UserResponse update(UserDTO userDTO, Long id) {
        checkIfIdExists(id);
        userDTO.setId(id);
        verifyIfDocumentExists(userDTO);
        findByEmail(userDTO);
        var user = repository.findById(id).get();
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setDocument(userDTO.getDocument());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        String profileName = "ROLE_"+userDTO.getProfileName().toUpperCase();
        Profile profileToRemove = user.getProfile().get(0);
        user.removeProfile(profileToRemove);
        Profile profile = profileRepository.findByName(profileName).get();
        user.addProfile(profile);
        repository.save(user);
        var response = mapper.map(user, UserResponse.class);
        response.setProfileName(userDTO.getProfileName());
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

    private void verifyIfDocumentExists(UserDTO userDTO){
        Optional<User> user = repository.findByDocument(userDTO.getDocument());
        if (user.isPresent() && !user.get().getId().equals(userDTO.getId())){
            throw new DataIntegrityValidationException("Documento já cadastrado no sistema!");
        }
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
        List<UserResponseGetAll> usersResponse = mapper.map(users, new TypeToken<List<UserResponseGetAll>>() {
        }.getType());
        return PageableDTO.builder().numberOfElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages()).usersResponse(usersResponse).build();

    }
    @Override
    public UserResponse findById(Long id){
        var user = getUser(id);
        var response  = mapper.map(user, UserResponse.class);
        response.setProfileName(user.getProfile().get(0).getName());
        return response;
    }

    public User getUser(Long id){
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new IdNotFoundException(id));

    }
}
