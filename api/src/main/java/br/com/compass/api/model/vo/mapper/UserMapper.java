package br.com.compass.api.model.vo.mapper;

import br.com.compass.api.model.Address;
import br.com.compass.api.model.User;
import br.com.compass.api.model.vo.CreateUserVO;
import br.com.compass.api.services.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ViaCepService viaCepService;

    @Autowired
    public UserMapper(ViaCepService viaCepService) {
        this.viaCepService = viaCepService;
    }

    public User createVoToUser(CreateUserVO vo) {
        if (vo == null) {
            return null;
        }

        Address address = viaCepService.getAddress(vo.getCep());
        return new User(vo.getUsername(), vo.getEmail(), vo.getPassword(), address);
    }

}
