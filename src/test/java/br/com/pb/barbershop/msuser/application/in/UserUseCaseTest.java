package br.com.pb.barbershop.msuser.application.in;

import br.com.pb.barbershop.msuser.application.ports.in.UserUseCase;
import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
public class UserUseCaseTest {
    @InjectMocks
    private UserUseCase userCase;
    @Mock
    private UserRepository userRepository;
    @Test
    void whenGetUserReturnUser(){
        User user = new User(1l, "João", "João@hotmail.com", "71987141798", "document");
        Mockito.when(userRepository.findById(ArgumentMatchers.eq(user.getId()))).thenReturn(Optional.of(user));
        User userTest = userCase.getUser(user.getId());
        Assertions.assertEquals(user.getName(), userTest.getName());
        Assertions.assertEquals(user.getEmail(), userTest.getEmail());

    }

}
