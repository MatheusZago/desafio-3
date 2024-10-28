package br.com.compass.api.controllers;

import br.com.compass.api.model.vo.CreateUserVO;
import br.com.compass.api.model.vo.ResponseUserVO;
import br.com.compass.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseUserVO registerUser(@RequestBody CreateUserVO vo){
        return service.registerUser(vo);
    }

}
