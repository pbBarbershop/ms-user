
package br.com.pb.barbershop.msuser.application.in;

import br.com.pb.barbershop.msuser.application.ports.in.UserUseCase;
import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.model.User;
import org.junit.jupiter.api.Assertions;
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
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UserUseCaseTest {

    @InjectMocks
    private UserUseCase userCase;

    @Mock
    private UserRepository userRepository;

    @Spy
    ModelMapper mapper;

    @Test
    void shouldFindAllUsers() {
        var user = new User();
        Page<User> page = new PageImpl<>(List.of(user));
        PageableDTO expectedPageableParameters = getStateResponseParameters();

        Mockito.when(userRepository.findAll((Pageable) any())).thenReturn(page);

        PageableDTO pageableParameters = userCase.findAll(null, any(Pageable.class));

        assertEquals(expectedPageableParameters.getNumberOfElements(), pageableParameters.getNumberOfElements());
        assertEquals(expectedPageableParameters.getTotalElements(), pageableParameters.getTotalElements());
        assertEquals(expectedPageableParameters.getTotalPages(), pageableParameters.getTotalPages());
        assertEquals(expectedPageableParameters.getUsersDTO().get(0).getId(), pageableParameters.getUsersDTO().get(0).getId());
    }

    private PageableDTO getStateResponseParameters() {
        return PageableDTO.builder()
                .numberOfElements(1)
                .totalElements(1L)
                .totalPages(1)
                .usersDTO(List.of(new UserDTO()))
                .build();
    }
    @Test
    void whenGetUserReturnUser(){
        User user = new User(1l, "João", "João@hotmail.com", "71987141798", "document");
        Mockito.when(userRepository.findById(ArgumentMatchers.eq(user.getId()))).thenReturn(Optional.of(user));
        User userTest = userCase.getUser(user.getId());
        Assertions.assertEquals(user.getName(), userTest.getName());
        Assertions.assertEquals(user.getEmail(), userTest.getEmail());
    }
}