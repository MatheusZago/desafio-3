package br.com.compass.api.controllers;

import br.com.compass.api.model.User;
import br.com.compass.api.model.vo.*;
import br.com.compass.api.services.JwtTokenService;
import br.com.compass.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

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

    @PostMapping("/login")
    public ResponseEntity<JwtResponseVo> authenticateUser(@RequestBody LoginRequestVO loginRequestVO) {
        JwtResponseVo token = service.authenticateUser(loginRequestVO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Void> deleteUsersByUsername(@PathVariable String username) {
        service.deleteUsersByUsername(username);
        return ResponseEntity.noContent().build();
    }

}
