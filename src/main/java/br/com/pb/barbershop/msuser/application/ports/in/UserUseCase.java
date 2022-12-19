package br.com.pb.barbershop.msuser.application.ports.in;

import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.model.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserUseCase implements UserService {

    private final UserRepository repository;

    private final ModelMapper mapper;

    @Override
    public PageableDTO findAll(String name, Pageable pageable) {
        Page<User> page = name==null?
                repository.findAll(pageable):repository.findByName(name, pageable);
        List<User> users = page.getContent();
        List<UserDTO> usersDTO = mapper.map(users, new TypeToken<List<UserDTO>>(){}.getType());
        return PageableDTO.builder().numberOfElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages()).usersDTO(usersDTO).build();
    }
}
