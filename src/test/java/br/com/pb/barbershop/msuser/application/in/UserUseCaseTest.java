package br.com.pb.barbershop.msuser.application.in;

import br.com.pb.barbershop.msuser.application.ports.out.ProfileRepositoryPortOut;
import br.com.pb.barbershop.msuser.application.ports.out.UserRepositoryPortOut;
import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserResponse;
import br.com.pb.barbershop.msuser.domain.dto.UserResponseGetAll;
import br.com.pb.barbershop.msuser.domain.model.Profile;
import br.com.pb.barbershop.msuser.domain.model.User;
import br.com.pb.barbershop.msuser.framework.exception.GenericException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserUseCaseTest {

    private static final Long ID      = Long.valueOf(1);
    private static final String NAME     = "michel";
    private static final String EMAIL    = "michel@mail.com";
    private static final String PHONE = "123";
    private static final String DOCUMENT = "12345";
    private static final String PASSWORD = "12345";


    private static final String E_MAIL_JA_CADASTRADO_NO_SISTEMA = "Email already registered in the system";


    @InjectMocks
    private UserService service;

    @Mock
    private UserRepositoryPortOut repository;
    @Mock
    private ProfileRepositoryPortOut profileRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    private ModelMapper mapper;


    private User user;
    private UserDTO userDTO;
    private UserResponse userResponse;
    private Optional<User> optionalUser;
    private List<Profile> profile;


    @Test
    void whenCreateThenReturnSucess(){
        UserDTO userDTO = getUserDTO();
        User user = getUser();
        Profile profile = getProfile();
        UserResponse userResponse = getUserResponse();
        when(mapper.map(userDTO, User.class)).thenReturn(user);
        when(mapper.map(user, UserResponse.class)).thenReturn(userResponse);
        when(mapper.map(Optional.of(profile),Profile.class)).thenReturn(profile);
        when(profileRepository.findByName(anyString())).thenReturn(Optional.of(profile));

        UserResponse response = service.create(userDTO);
        Assertions.assertEquals(user.getName(), response.getName());
        Assertions.assertEquals(user.getPhone(), response.getPhone());
        Assertions.assertEquals(user.getDescription(), response.getDescription());

    }

    @Test
    void whenUpdateThenReturnSucess() {
        var userDTO = getUserDTO();
        var user = getUser();
        Profile profile = getProfile();
        UserResponse userResponse = getUserResponse();

        when(mapper.map(user, UserResponse.class)).thenReturn(userResponse);

        when(service.create(userDTO)).thenReturn(userResponse);


        when(profileRepository.findByName(anyString())).thenReturn(Optional.of(profile));

        when(repository.findById(any())).thenReturn(Optional.of(user));
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));

        UserResponse response = service.update(userDTO, ID);

        Assertions.assertEquals(user.getId(), response.getId());
        Assertions.assertEquals(user.getName(), response.getName());

    }

    @Test
    void whenGetUserReturnUser(){
        User user = new User(ID, NAME, EMAIL, PHONE, DOCUMENT, PASSWORD, profile);
        when(repository.findById(ArgumentMatchers.eq(user.getId()))).thenReturn(Optional.of(user));
        User userTest = service.getUser(user.getId());
        assertEquals(user.getName(), userTest.getName());
        assertEquals(user.getEmail(), userTest.getEmail());
    }


        private UserResponse getUserResponse() {
            return UserResponse.builder()
                    .id(ID)
                    .name("Lucas")
                    .phone("71992361965")
                    .email("lucas@hotmail.com")
                    .profileName("MANAGER")
                    .description("Isto é uma descrição")
                    .build();
        }

    private UserDTO getUserDTO() {
        return UserDTO.builder().name("Lucas")
                .email("lucas@hotmail.com")
                .password("12345678")
                .phone("71992367865")
                .description("Isto é uma descrição")
                .profileName("MANAGER")
                .build();
    }

    private User getUser() {
        return User.builder().name("Lucas")
                .id(ID)
                .email("lucas@hotmail.com")
                .password("12345678")
                .phone("71992367865")
                .description("Isto é uma descrição")
                .profile(List.of(new Profile(1l, "ROLE_MANAGER")))
                .build();
    }

    private Profile getProfile(){
        return Profile.builder()
                .id(1L)
                .name("ROLE_MANAGER")
                .build();
    }

}



