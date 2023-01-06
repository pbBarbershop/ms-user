package br.com.pb.barbershop.msuser.application.ports.in;

import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserResponse;
import org.springframework.data.domain.Pageable;

public interface UserUseCase {

    UserResponse findById(Long id);

    void deleteUserId(Long id);

    PageableDTO findAll(String name, Pageable pageable);

    UserResponse create(UserDTO obj);

    UserResponse update(UserDTO obj, Long id);



}









