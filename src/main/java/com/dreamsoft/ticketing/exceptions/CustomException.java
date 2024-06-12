package com.dreamsoft.ticketing.exceptions;

import com.dreamsoft.ticketing.entity.enumerations.CustomMessage;
import org.springframework.http.HttpStatus;

public class CustomException extends Exception{
    protected String code;

    protected String message;

    protected HttpStatus status;

    protected Object args;
    public CustomException(CustomMessage message) {
        this.code = message.getCode();
        this.status = HttpStatus.resolve(message.getHttpStatus());
        this.message = message.getMessage();
    }

    public CustomException(String code, String message,HttpStatus status ) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public CustomException(CustomMessage message, Object args) {
        this.code = message.getCode();
        this.status = HttpStatus.resolve(message.getHttpStatus());
        this.message = message.getMessage()+args.toString();
        //  this.args = args;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getArgs() {
        return args;
    }

    public void setArgs(Object args) {
        this.args = args;
    }
}
