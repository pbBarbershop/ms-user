package br.com.pb.barbershop.msuser.application.ports.in;

import br.com.pb.barbershop.msuser.application.ports.out.UserRepository;
import br.com.pb.barbershop.msuser.application.service.UserService;
import br.com.pb.barbershop.msuser.framework.exception.IdNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUseCase implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private void checkIfIdExists(Long id){
        userRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }

    public void deleteUserId(Long id){
        checkIfIdExists(id);
        userRepository.deleteById(id);
    }

}
