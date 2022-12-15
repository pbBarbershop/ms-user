package br.com.pb.barbershop.msuser.in;

import br.com.pb.barbershop.msuser.application.ports.in.UserUseCase;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.model.User;
import br.com.pb.barbershop.msuser.framework.exception.DataIntegrityValidationException;
import br.com.pr.barbershop.msuser.application.ports.out.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

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


    private static final String E_MAIL_JA_CADASTRADO_NO_SISTEMA = "Email already registered in the system";


    @InjectMocks
    private UserUseCase useCase;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenUpdateThenReturnSucess(){
        when(repository.save(any())).thenReturn(user);

        User response = useCase.update(userDTO);

        assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
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
            useCase.update(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegrityValidationException.class, ex.getClass());
            assertEquals("Email already registered in the system", ex.getMessage());
        }


    }
    private void startUser() {

        user = new User();
        userDTO = new UserDTO();
        optionalUser = Optional.of(new User());
        }
    }
