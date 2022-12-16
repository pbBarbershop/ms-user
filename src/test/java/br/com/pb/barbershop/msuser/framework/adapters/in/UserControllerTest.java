package br.com.pb.barbershop.msuser.framework.adapters.in;

import br.com.pb.barbershop.msuser.application.ports.in.UserUseCase;
import br.com.pb.barbershop.msuser.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserUseCase userCase;

    private static final String URL = "/user";

    @Test
    public void shouldListAllTheUsers() throws Exception {
        List<User> list = new ArrayList<>();
        when(userCase.findAll()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(200));
    }
}
