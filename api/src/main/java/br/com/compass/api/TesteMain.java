package br.com.compass.api;

import br.com.compass.api.model.Address;
import br.com.compass.api.services.ViaCepService;
import org.springframework.boot.SpringApplication;

public class TesteMain {

    public static void main(String[] args) {
        //SpringApplication.run(ApiApplication.class, args);

        ViaCepService viaCep = new ViaCepService();

        Address address = viaCep.getAddress("01001000");

        System.out.print(address);
    }

}
