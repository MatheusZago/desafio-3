package br.com.compass.api.model.vo;

import jakarta.validation.constraints.NotBlank;


public class LoginRequestVO {

    @NotBlank(message = "The username cannot be blank.")
    private String username;

    @NotBlank(message = "The password cannot be blank.")
    private String password;

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
}
