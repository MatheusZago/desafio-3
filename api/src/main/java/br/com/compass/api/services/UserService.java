package br.com.compass.api.services;

import br.com.compass.api.config.SecurityConfig;
import br.com.compass.api.exceptions.DuplicateUserException;
import br.com.compass.api.exceptions.PasswordMismatchException;
import br.com.compass.api.exceptions.UserNotFoundException;
import br.com.compass.api.jwt.UserDetailsImpl;
import br.com.compass.api.kafka.KafkaProducer;
import br.com.compass.api.model.User;
import br.com.compass.api.model.vo.*;
import br.com.compass.api.model.vo.mapper.UserMapper;
import br.com.compass.api.repositories.UserRepository;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public ResponseUserVO registerUser(@RequestBody CreateUserVO vo) {
        User user = mapper.createVoToUser(vo);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            repository.save(user);
        } catch (DataIntegrityViolationException e) {

            throw new DuplicateUserException(vo.getUsername());
        }

        ResponseUserVO responseUserVO = mapper.userToResponseVO(user);

        String message = "User: " + user.getUsername() + " used a CREATE function";
        kafkaProducer.sendMessage(message);

        return responseUserVO;
    }

    @PutMapping
    public void updatePassword(@RequestBody UpdateRequestVO vo) {
        Optional<User> optionalUser = repository.findByUsername(vo.getUsername());

        optionalUser.ifPresentOrElse(user -> {
            if (passwordEncoder.matches(vo.getOldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(vo.getNewPassword()));

                String message = "User: " + user.getUsername() + " used an UPDATE function";
                kafkaProducer.sendMessage(message);
                repository.save(user);
            } else {
                throw new PasswordMismatchException();
            }
        }, () -> {
            throw new UserNotFoundException(vo.getUsername());
        });
    }

    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public JwtResponseVo authenticateUser(LoginRequestVO loginRequestVO) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequestVO.getUsername(), loginRequestVO.getPassword());

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return new JwtResponseVo(jwtTokenService.generateToken(userDetails));
    }

}

