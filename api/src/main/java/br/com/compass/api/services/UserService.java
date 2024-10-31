package br.com.compass.api.services;

import br.com.compass.api.config.SecurityConfig;
import br.com.compass.api.jwt.UserDetailsImpl;
import br.com.compass.api.kafka.KafkaProducer;
import br.com.compass.api.model.User;
import br.com.compass.api.model.vo.*;
import br.com.compass.api.model.vo.mapper.UserMapper;
import br.com.compass.api.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository repository;
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseUserVO registerUser(CreateUserVO vo){
        User user = mapper.createVoToUser(vo);

        // Codifica a senha antes de salvar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);

        ResponseUserVO responseUserVO = mapper.userToResponseVO(user);

        String message = "User: " + user.getUsername() + " used a CREATE function";
        kafkaProducer.sendMessage(message);

        return responseUserVO;
    }

    @PutMapping
    public void updatePassword(@RequestBody UpdateRequestVO vo) {
        // Buscando o usuário
        Optional<User> optionalUser = repository.findByUsername(vo.getUsername());

        // Verificando se o usuário está presente
        optionalUser.ifPresentOrElse(user -> {
            // Verifica se a senha antiga está correta
            if (passwordEncoder.matches(vo.getOldPassword(), user.getPassword())) {
                // Codifica a nova senha e salva
                user.setPassword(passwordEncoder.encode(vo.getNewPassword()));

                String message = "User: " + user.getUsername() + " used an UPDATE function";
                kafkaProducer.sendMessage(message);
                repository.save(user);
            } else {
                log.error("Password Error");
                // TODO: Lançar uma exceção customizada aqui
            }
        }, () -> {
            log.error("User not found");
            // TODO: Lançar uma exceção customizada para usuário não encontrado
        });
    }

    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public JwtResponseVo authenticateUser(LoginRequestVO loginRequestVO) {
            // Cria um objeto de autenticação com o email e a senha do usuário
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequestVO.getUsername(), loginRequestVO.getPassword());

            // Autentica o usuário com as credenciais fornecidas
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            // Obtém o objeto UserDetails do usuário autenticado
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Gera um token JWT para o usuário autenticado
            return new JwtResponseVo(jwtTokenService.generateToken(userDetails));
    }
}

