package br.com.pb.barbershop.msuser.application.service;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import org.springframework.data.domain.Pageable;


import br.com.pb.barbershop.msuser.domain.dto.UserResponse;

public interface UserService {

    UserResponse findById(Long id);

    void deleteUserId(Long id);

    PageableDTO findAll(String name, Pageable pageable);

    UserResponse create(UserDTO obj);

    UserResponse update(UserDTO obj, Long id);



}









