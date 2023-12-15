package com.example.projectbase.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InsufficientStockException extends RuntimeException {
    private String message;

    private HttpStatus status;

    private String[] params;

    public InsufficientStockException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
        this.message = message;
    }

    public InsufficientStockException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public InsufficientStockException(String message, String[] params) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
        this.message = message;
        this.params = params;
    }

    public InsufficientStockException(HttpStatus status, String message, String[] params) {
        super(message);
        this.status = status;
        this.message = message;
        this.params = params;
    }
}
