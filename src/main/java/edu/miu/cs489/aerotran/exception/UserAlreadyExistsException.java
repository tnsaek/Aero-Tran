package edu.miu.cs489.aerotran.exception;

public class UserAlreadyExistsException extends Exception{
    private String message;

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
