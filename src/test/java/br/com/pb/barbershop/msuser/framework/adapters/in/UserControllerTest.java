package br.com.pb.barbershop.msuser.framework.adapters.in;

import br.com.pb.barbershop.msuser.application.ports.in.UserUseCase;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserControllerTest {

    private static final Long ID         = Long.valueOf(1);
    private static final String NAME     = "michel";
    private static final String EMAIL    = "michel@mail.com";
    private static final String PHONE    = "123";
    private static final String DOCUMENT = "12345";
    private User user = new User();
    private UserDTO userDTO = new UserDTO();
    @InjectMocks
    private UserController controller;
    @Mock
    private UserUseCase useCase;
    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(useCase.create(any())).thenReturn(user);

        ResponseEntity<UserDTO> response = controller.create(userDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSucess() {
        when(useCase.update(userDTO)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = controller.update((long) ID, userDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().setId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PHONE, response.getBody().getPhone());
        assertEquals(DOCUMENT, response.getBody().getDocument());

    }
    private void startUser() {

        user = new User(ID,NAME,EMAIL,PHONE,DOCUMENT);
        userDTO = new UserDTO(ID,NAME,EMAIL,PHONE,DOCUMENT);
    }
}