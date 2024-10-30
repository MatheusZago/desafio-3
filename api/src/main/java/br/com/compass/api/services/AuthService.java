package br.com.compass.api.services;

import br.com.compass.api.model.vo.JwtResponseVo;
import br.com.compass.api.model.vo.LoginRequestVO;
import br.com.compass.api.jwt.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    public JwtResponseVo authenticateUser(LoginRequestVO loginRequest) {
        // Autentica o usuário
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Obtém o UserDetailsImpl do contexto de autenticação
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera o token usando o UserDetailsImpl
        String token = jwtTokenService.generateToken(userDetails);
        return new JwtResponseVo(token);
    }
}
