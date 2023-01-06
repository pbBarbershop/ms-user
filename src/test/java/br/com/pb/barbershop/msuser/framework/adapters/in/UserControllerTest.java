
package br.com.pb.barbershop.msuser.framework.adapters.in;
import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserResponse;
import br.com.pb.barbershop.msuser.domain.model.User;
import br.com.pb.barbershop.msuser.framework.adapters.in.rest.UserController;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private static final Long ID  = 1L;
    private static final String URL = "/user";
    private static final String URL_ID = URL+"/1";
    private UserResponse userResponse;

    @MockBean
    private UserService service;
    @Autowired
    private MockMvc mockMvc;

    private Gson gson;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gson = new Gson();
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void whenCreateThenReturnCreated() throws Exception {
        var userDTO = getUserDTO();
        var userResponse = getUserResponse();
        when(service.create(userDTO)).thenReturn(userResponse);
        var request = gson.toJson(userDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void whenUpdateThenReturnSucess() throws Exception {
        var userDto = getUserDTO();
        var userResponse = getUserResponse();

        when(service.update(userDto, ID)).thenReturn(userResponse);
        var request = gson.toJson(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(URL_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());

    }



    @Test
    @WithMockUser(roles = "MANAGER")
    void findByIdTest() throws Exception {
        userResponse = getUserResponse();
        when(service.findById(any())).thenReturn(userResponse);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }


    @Test
    @WithMockUser(roles = "MANAGER")
    void shouldListAllTheUsers() throws Exception {
        PageableDTO pageableDTO = new PageableDTO();

        when(service.findAll(any(), any())).thenReturn(pageableDTO);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    @WithMockUser(roles = "MANAGER")
    void deleteTest() throws Exception {
        doNothing().when(service).deleteUserId(ID);

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .delete(URL_ID)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
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

}
