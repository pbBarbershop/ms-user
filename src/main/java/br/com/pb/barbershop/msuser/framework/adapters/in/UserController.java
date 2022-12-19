package br.com.pb.barbershop.msuser.framework.adapters.in;

import br.com.pb.barbershop.msuser.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserId (@PathVariable Long id){
        userService.deleteUserId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
