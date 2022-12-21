package br.com.pb.barbershop.msuser.application.service;
import br.com.pb.barbershop.msuser.domain.dto.UserDTO;
import br.com.pb.barbershop.msuser.domain.model.User;
import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import org.springframework.data.domain.Pageable;

public interface UserService {

    public UserDTO findById(Long id);


    User create(UserDTO obj);

    User update(User dto);

    void deleteUserId(Long id);

    PageableDTO findAll(String name, Pageable pageable);

}









