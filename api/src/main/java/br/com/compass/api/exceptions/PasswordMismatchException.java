package br.com.compass.api.exceptions;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
      super("The new password does not match the old one.");
    }
}
