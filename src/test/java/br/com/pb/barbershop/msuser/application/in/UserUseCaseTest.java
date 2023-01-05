package br.com.pb.barbershop.msuser.application.in;

import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserResponse;
import br.com.pb.barbershop.msuser.domain.dto.UserResponseGetAll;
import br.com.pb.barbershop.msuser.domain.model.Profile;
import br.com.pb.barbershop.msuser.domain.model.User;
import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
    private UserService useCase;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private UserResponse userResponse;
    private Optional<User> optionalUser;
    private List<Profile> profile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenUpdateThenReturnSucess(){
        when(repository.save(any())).thenReturn(user);

        UserResponse response = useCase.update(userDTO, 1l);

        assertNotNull(response);
        Assertions.assertEquals(UserResponse.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(PHONE, response.getPhone());
        Assertions.assertEquals(DOCUMENT, response.getDocument());


    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2L);
            useCase.update(userDTO, 2L);
        } catch (Exception ex) {
            assertEquals(DataIntegrityValidationException.class, ex.getClass());
            assertEquals("Email already registered in the system", ex.getMessage());
        }


    }

    @Test
    void whenGetUserReturnUser(){
        User user = new User(ID, NAME, EMAIL, PHONE, DOCUMENT, PASSWORD, profile);
        when(repository.findById(ArgumentMatchers.eq(user.getId()))).thenReturn(Optional.of(user));
        User userTest = useCase.getUser(user.getId());
        assertEquals(user.getName(), userTest.getName());
        assertEquals(user.getEmail(), userTest.getEmail());
    }

    @Test
    void shouldFindAllUsers() {
        var user = new User();
        Page<User> page = new PageImpl<>(List.of(user));
        PageableDTO expectedPageableParameters = getStateResponseParameters();

        when(repository.findAll((Pageable) any())).thenReturn(page);

        PageableDTO pageableParameters = useCase.findAll(null, any(Pageable.class));

        assertEquals(expectedPageableParameters.getNumberOfElements(), pageableParameters.getNumberOfElements());
        assertEquals(expectedPageableParameters.getTotalElements(), pageableParameters.getTotalElements());
        assertEquals(expectedPageableParameters.getTotalPages(), pageableParameters.getTotalPages());
        assertEquals(expectedPageableParameters.getUsersResponse().get(0).getId(), pageableParameters.getUsersResponse().get(0).getId());
    }

    private PageableDTO getStateResponseParameters() {
        return PageableDTO.builder()
                .numberOfElements(1)
                .totalElements(1L)
                .totalPages(1)
                .usersResponse(List.of(new UserResponseGetAll()))
                .build();
    }

    private void startUser() {

        user = new User();
        userDTO = new UserDTO();
        userResponse = new UserResponse();
        profile.add(new Profile());
        optionalUser = Optional.of(new User());
        }
    }


