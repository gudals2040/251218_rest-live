package kr.java.restapi.exception;

// #(3)-3
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
