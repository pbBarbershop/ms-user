package br.com.pb.barbershop.msuser.application.service;

import br.com.pb.barbershop.msuser.domain.dto.PageableDTO;
import org.springframework.data.domain.Pageable;

public interface UserService {

     PageableDTO findAll(String name, Pageable pageable);
}







