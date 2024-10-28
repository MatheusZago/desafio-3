package br.com.compass.api.model.vo;

import br.com.compass.api.model.Address;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "email", "address"})
public class ResponseUserVO {

    private String username;
    private String email;
    private Address address;

    public ResponseUserVO(){}

    public ResponseUserVO(String username, String email, Address address) {
        this.username = username;
        this.email = email;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
