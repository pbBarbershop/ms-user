package br.com.pb.barbershop.msuser.application.service;

import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.dto.UserResponse;
import br.com.pb.barbershop.msuser.domain.model.User;

public interface UserService {

    UserResponse create(UserDTO obj);

    UserResponse update(UserDTO obj, Long id);


}







