package br.com.compass.api.model;

import java.io.Serializable;

public class NotifyMessage implements Serializable {
    private static final long serialVersionUID = 1L;


    private String username;
    private String operation;

    public NotifyMessage() {
    }

    public NotifyMessage(String username, String operation) {
        this.username = username;
        this.operation = operation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
