package br.com.compass.api.controllers;

import br.com.compass.api.model.vo.CreateUserVO;
import br.com.compass.api.model.vo.ResponseUserVO;
import br.com.compass.api.model.vo.UpdateRequestVO;
import br.com.compass.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<ResponseUserVO> registerUser(@RequestBody CreateUserVO vo){
        System.out.println("Chamando o método de registro de usuário...");
        return ResponseEntity.ok(service.registerUser(vo));
    }

    @PutMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdateRequestVO vo){

        service.updatePassword(vo);
        return ResponseEntity.noContent().build();
    }

}
