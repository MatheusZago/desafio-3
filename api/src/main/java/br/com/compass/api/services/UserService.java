package br.com.compass.api.services;

import br.com.compass.api.kafka.KafkaProducer;
import br.com.compass.api.model.NotifyMessage;
import br.com.compass.api.model.User;
import br.com.compass.api.model.vo.CreateUserVO;
import br.com.compass.api.model.vo.ResponseUserVO;
import br.com.compass.api.model.vo.UpdateRequestVO;
import br.com.compass.api.model.vo.mapper.UserMapper;
import br.com.compass.api.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository repository;
    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserMapper mapper;

    @PostMapping
    public ResponseUserVO registerUser(CreateUserVO vo){

        User user = mapper.createVoToUser(vo);

        repository.save(user);

        ResponseUserVO responseUserVO = mapper.userToResponseVO(user);

//        NotifyMessage message = new NotifyMessage(vo.getUsername(), "CREATE");
        String message = user.getUsername() + ",CREATE";
        kafkaProducer.sendMessage(message);

        return responseUserVO;
    }

    @PutMapping
    public void updatePassword(UpdateRequestVO vo){
        User user = repository.findByUsernameAndPassword(vo.getUsername(), vo.getOldPassword());


        if(user.getPassword().equals(vo.getOldPassword())){
            user.setPassword(vo.getNewPassword());
            System.out.print(user.getPassword());

//            NotifyMessage message = new NotifyMessage(vo.getUsername(), "UPDATE");
            String message = user.getUsername() + ",UPDATE";
            kafkaProducer.sendMessage(message);
            repository.save(user);
        }else {
            // TODO Criar exception.
            log.error("Erro nas senhas");
        }

    }

}
