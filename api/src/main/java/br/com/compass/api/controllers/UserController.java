package br.com.compass.api.controllers;

import br.com.compass.api.model.User;
import br.com.compass.api.model.vo.CreateUserVO;
import br.com.compass.api.model.vo.ResponseUserVO;
import br.com.compass.api.model.vo.UpdateRequestVO;
import br.com.compass.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<ResponseUserVO> registerUser(@RequestBody CreateUserVO vo){
        return ResponseEntity.ok(service.registerUser(vo));
    }

    @PutMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdateRequestVO vo){

        service.updatePassword(vo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());
    }

}
