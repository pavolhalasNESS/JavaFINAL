package sk.ness.academy.exception;

import org.springframework.http.HttpStatus;
public class RequestException extends RuntimeException {
    private final HttpStatus httpStatus;
    public RequestException(HttpStatus status, String msg) {
        super(msg);
        this.httpStatus = status;
    }

}