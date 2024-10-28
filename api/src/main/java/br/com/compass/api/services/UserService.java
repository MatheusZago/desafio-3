package br.com.compass.api.services;

import br.com.compass.api.model.User;
import br.com.compass.api.model.vo.CreateUserVO;
import br.com.compass.api.model.vo.mapper.UserMapper;
import br.com.compass.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @PostMapping
    public User registerUser(CreateUserVO vo){

        User user = mapper.createVoToUser(vo);

        return repository.save(user);
    }

}
