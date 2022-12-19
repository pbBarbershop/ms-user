package br.com.pb.barbershop.msuser.application.in;

import br.com.pb.barbershop.msuser.application.ports.in.UserUseCase;
import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
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

//      assertEquals(expectedPageableParameters, pageableParameters);
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
}
