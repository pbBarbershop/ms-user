package br.com.pb.barbershop.msuser.application.service;

import br.com.pb.barbershop.msuser.application.ports.in.UserUseCase;
import br.com.pb.barbershop.msuser.application.ports.out.ProfileRepositoryPortOut;
import br.com.pb.barbershop.msuser.application.ports.out.UserRepositoryPortOut;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserResponse;
import br.com.pb.barbershop.msuser.domain.dto.UserResponseGetAll;
import br.com.pb.barbershop.msuser.domain.model.Profile;
import br.com.pb.barbershop.msuser.domain.model.User;
import br.com.pb.barbershop.msuser.framework.exception.GenericException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {
    private final ModelMapper mapper;

    private final UserRepositoryPortOut repository;
    private final ProfileRepositoryPortOut profileRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserDTO userDTO) {
        findByEmail(userDTO);

        var user = new User();
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setDescription(userDTO.getDescription());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        String profileName = "ROLE_"+userDTO.getProfileName().toUpperCase();
        Optional<Profile> profile = profileRepository.findByName(profileName);
        Profile profileSave = mapper.map(profile, Profile.class);
        user.addProfile(profileSave);
        repository.save(user);

        var response = mapper.map(user, UserResponse.class);
        response.setProfileName(userDTO.getProfileName());

        return response;
    }
    @Override
    public UserResponse update(UserDTO userDTO, Long id) {
        User user = repository
                .findById(id)
                .orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "Id n??o encontrado!"));

        userDTO.setId(id);
        findByEmail(userDTO);

        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setDescription(userDTO.getDescription());
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
        return user.orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "Usu??rio n??o existente!"));

    }

    private void findByEmail(UserDTO obj) {
        Optional<User> user = repository.findByEmail(obj.getEmail());
        if (user.isPresent() && !user.get().getId().equals(obj.getId())) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "Email em uso!");
        }
    }
    private User checkIfIdExists(Long id){
        return repository.findById(id).orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "Usu??rio n??o existente"));
    }

}
