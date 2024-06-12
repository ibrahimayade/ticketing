package com.dreamsoft.ticketing.exceptions;

import com.dreamsoft.ticketing.api.response.TicketingError;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
public class CustomError {
    private HttpStatus status;
    private String message;
    private List<TicketingError> errors;
    public CustomError(){

    }
    public CustomError(HttpStatus status, String message, List<TicketingError> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public CustomError(HttpStatus status, String message, TicketingError error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}
