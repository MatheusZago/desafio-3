package br.com.compass.api.model.vo;

public class JwtResponseVo {

    private String token;

    public JwtResponseVo(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
