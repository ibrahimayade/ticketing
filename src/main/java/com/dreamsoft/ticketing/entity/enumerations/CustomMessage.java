package com.dreamsoft.ticketing.entity.enumerations;

import org.springframework.http.HttpStatus;

public enum CustomMessage {

    INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), "9000", "Invalid token"),
    INTERNAL_ERROR(HttpStatus.BAD_REQUEST.value(), "9001", "INTERNAL ERROR"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "1001", "USER NOT FOUND"),
    NOT_ALLOWED(HttpStatus.FORBIDDEN.value(), "40301", "action not allowed"),

    TICKET_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "404002", "TIKCET NOT FOUND"),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST.value(), "400001", "USER ALREADY EXIST"),
    TICKET_ALREADY_EXIST(HttpStatus.BAD_REQUEST.value(), "400002", "TICKET ALREADY EXIST"),

    MUST_BE_PROVIED(HttpStatus.BAD_REQUEST.value(), "400003", "MUST  BE PROVIDED"),

    MISSING_PARAM(HttpStatus.BAD_REQUEST.value(), "400004", "MISSING PARAMS"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST.value(), "400005", "VIALATION  ERROR"),

    METHOD_ARGUMENT_TYPE(HttpStatus.BAD_REQUEST.value(), "400006", "METHOD ARGUMENT ERROR"),

    NO_HANDLING(HttpStatus.BAD_REQUEST.value(), "400007", "NO HANDLING"),
    UNSUPPORTED_METHOD(HttpStatus.BAD_REQUEST.value(), "400008", "UNSUPPORTED METHOD"),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.BAD_REQUEST.value(), "400009", "UNSUPPORTED MEDIA TYPE"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED.value(), "401002", "Invalid credentials")
    ;
    private int httpStatus;
    private String code;
    private String message;

    CustomMessage(int httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
