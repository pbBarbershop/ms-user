package br.com.pb.barbershop.msuser.application.in;

import br.com.pb.barbershop.msuser.application.ports.in.UserUseCase;
import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import br.com.pb.barbershop.msuser.domain.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserUseCaseTest {

    @InjectMocks
    private UserUseCase userCase;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldReturnAllUsers() {

        var user = new User();
        List<User> list = new ArrayList<>();

        user.setId(1l);
        user.setName("João");
        user.setEmail("Joao@gmail.com");
        user.setPhone("894223824");
        user.setDocument("3423526");
        list.add(user);

        Mockito.when(userRepository.findAll()).thenReturn(list);

        List<User> AllUsersList = userCase.findAll();

        assertEquals(list, AllUsersList);
    }
}
