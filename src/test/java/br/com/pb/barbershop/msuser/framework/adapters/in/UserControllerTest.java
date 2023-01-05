
package br.com.pb.barbershop.msuser.framework.adapters.in;
import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserResponse;
import br.com.pb.barbershop.msuser.domain.model.User;
import br.com.pb.barbershop.msuser.framework.adapters.in.rest.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private static final Long ID         = 1L;
    private static final String NAME     = "michel";
    private static final String EMAIL    = "michel@mail.com";
    private static final String PHONE    = "123";
    private static final String DOCUMENT = "12345";
    private static final String PASSWORD = "12345";
    private static final String profileName = "Manager";

    private static final String URL = "/user";
    private static final String URL_ID = URL+"/1";
    private User user = new User();
    private UserResponse userResponse;
    private UserDTO userDTO = new UserDTO();
    List profile = new ArrayList();
    @InjectMocks
    private UserController controller;
    @Mock
    private UserService useCase;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(useCase.create(any())).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = controller.create(userDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSucess() {
        when(useCase.update(userDTO, ID)).thenReturn(userResponse);
        when(mapper.map(any(), any())).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = controller.update(ID, userDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PHONE, response.getBody().getPhone());
        assertEquals(DOCUMENT, response.getBody().getDocument());

    }

    @Test
    void findByIdTest() throws Exception {
        userResponse = new UserResponse();
        when(useCase.findById(any())).thenReturn(userResponse);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
    private void startUser() {
        List profile = new ArrayList();
        user = new User(ID,NAME,EMAIL,PHONE,DOCUMENT,PASSWORD,profile);
        userDTO = new UserDTO(ID,NAME,EMAIL,PHONE,DOCUMENT, PASSWORD, profileName);
    }

    @Test
    void shouldListAllTheUsers() throws Exception {
        PageableDTO pageableDTO = new PageableDTO();

        when(useCase.findAll(any(), any())).thenReturn(pageableDTO);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
