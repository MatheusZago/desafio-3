package br.com.compass.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table( name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String username;
    private String email;
    private String password;
    private String cep;

    public User(){}

    public User(String username, String email, String password, String cep) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.cep = cep;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(cep, user.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password, cep);
    }
}
