package br.com.compass.api.services;

import br.com.compass.api.model.User;
import br.com.compass.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @PostMapping
    public User registerUser(User user){
        repository.save(user);
        return user;
    }

}
