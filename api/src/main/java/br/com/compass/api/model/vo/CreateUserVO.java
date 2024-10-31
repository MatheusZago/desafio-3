package br.com.compass.api.model.vo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonPropertyOrder({"id", "title" ,"author", "launchdate", "price"})
public class CreateUserVO {

    private String username;
    private String password;
    private String email;
    private String cep;

    public CreateUserVO(){}

    public CreateUserVO(String username, String password, String email, String cep) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.cep = cep;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateUserVO that = (CreateUserVO) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(email, that.email) && Objects.equals(cep, that.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, cep);
    }
}
