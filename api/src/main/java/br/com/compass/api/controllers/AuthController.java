package br.com.compass.api.controllers;

import br.com.compass.api.jwt.UserDetailsImpl;
import br.com.compass.api.model.vo.JwtResponseVo;
import br.com.compass.api.model.vo.LoginRequestVO;
import br.com.compass.api.services.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestVO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Extraia o UserDetailsImpl do Authentication
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gere o token usando UserDetailsImpl
        String token = jwtTokenService.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseVo(token));
    }
}