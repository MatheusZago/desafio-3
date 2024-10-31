package br.com.compass.api.exceptions;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {

      super("Username: " + message + " already in use.");
    }
}
