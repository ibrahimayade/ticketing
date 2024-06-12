package com.dreamsoft.ticketing.exceptions;

import com.dreamsoft.ticketing.api.response.CustomeResponse;
import com.dreamsoft.ticketing.api.response.TicketingError;
import com.dreamsoft.ticketing.entity.enumerations.CustomMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<CustomeResponse> handleConflict(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof CustomException) {
            return handleCustomeException(headers, (CustomException) ex);
        }

        return handleException();
    }

    private ResponseEntity<CustomeResponse> handleCustomeException(HttpHeaders headers, CustomException customException) {

        final TicketingError error = new TicketingError();
        error.setCode(customException.getCode());
        error.setMessage(customException.getMessage());

        final CustomeResponse response = new CustomeResponse();
        response.setError(error);
        response.setSuccess(false);
        response.setData(customException.getArgs());

        return new ResponseEntity<CustomeResponse>(response, headers, customException.getStatus());
    }

    public ResponseEntity<CustomeResponse> handleException() {

        HttpHeaders headers = new HttpHeaders();

        final TicketingError error = new TicketingError();
        error.setCode(CustomMessage.INTERNAL_ERROR.getCode());
        error.setMessage(CustomMessage.INTERNAL_ERROR.getMessage());

        final CustomeResponse response = new CustomeResponse();
        response.setError(error);
        response.setSuccess(false);

        return new ResponseEntity<CustomeResponse>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<TicketingError> errors = new ArrayList<TicketingError>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            TicketingError customeError=new TicketingError();
            customeError.setMessage( error.getDefaultMessage());
            customeError.setCode("ERR-"+error.getField());
            errors.add(customeError);
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            TicketingError customeError=new TicketingError();
            customeError.setMessage( error.getDefaultMessage());
            customeError.setCode("ERR-"+error.getObjectName());
            errors.add(customeError);
        }

        CustomError customError = new CustomError(HttpStatus.BAD_REQUEST, ex.getFieldError().getDefaultMessage(), errors);
        TicketingError ticketingError=new TicketingError();
        ticketingError.setCode(CustomMessage.MUST_BE_PROVIED.getCode());
        ticketingError.setMessage(CustomMessage.MUST_BE_PROVIED.getMessage());

        CustomeResponse response=new CustomeResponse();
        response.setData(errors);
        response.setSuccess(false);
        response.setError(ticketingError);

        // HttpStatus.BAD_REQUEST, ex.getFieldError().getDefaultMessage(),customError);
        return handleExceptionInternal(
                ex, response, headers, customError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        TicketingError error=new TicketingError();
        error.setMessage( "parameter is missing");
        error.setCode("ERR-"+ex.getParameterName());

        CustomError customError =
                new CustomError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        TicketingError ticketingError=new TicketingError();
        ticketingError.setCode(CustomMessage.MISSING_PARAM.getCode());
        ticketingError.setMessage(CustomMessage.MISSING_PARAM.getMessage());
        CustomeResponse response=new CustomeResponse();
        response.setData(error);
        response.setSuccess(false);
        response.setError(ticketingError);

        return new ResponseEntity<Object>(
                customError, new HttpHeaders(), customError.getStatus());
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<TicketingError> errors = new ArrayList<TicketingError>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            TicketingError ticketingError=new TicketingError();
            ticketingError.setMessage(violation.getMessage());
            ticketingError.setCode("ERR-"+violation.getPropertyPath());
            errors.add(ticketingError);

        }

        CustomError customError =
                new CustomError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        TicketingError ticketingError=new TicketingError();
        ticketingError.setCode(CustomMessage.VALIDATION_ERROR.getCode());
        ticketingError.setMessage(CustomMessage.VALIDATION_ERROR.getMessage());
        CustomeResponse response=new CustomeResponse();
        response.setData(errors);
        response.setSuccess(false);
        response.setError(ticketingError);
        return new ResponseEntity<Object>(
                customError, new HttpHeaders(), customError.getStatus());
    }
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        TicketingError error =new TicketingError();
        error.setCode("ERR-"+ex.getRequiredType().getName());
        error.setMessage( ex.getName() + " should be of type " + ex.getRequiredType().getName());
        //     ex.getName() + " should be of type " + ex.getRequiredType().getName();

        CustomError customError =
                new CustomError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        TicketingError ticketingError=new TicketingError();
        ticketingError.setCode(CustomMessage.METHOD_ARGUMENT_TYPE.getCode());
        ticketingError.setMessage(CustomMessage.METHOD_ARGUMENT_TYPE.getMessage());
        CustomeResponse response=new CustomeResponse();
        response.setData(error);
        response.setSuccess(false);
        response.setError(ticketingError);
        return new ResponseEntity<Object>(
                customError, new HttpHeaders(), customError.getStatus());
    }
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String msg = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        TicketingError error =new TicketingError();
        error.setCode("ERR-"+ex.getHttpMethod());
        error.setMessage(msg);

        CustomError apiError = new CustomError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        TicketingError ticketingError=new TicketingError();
        ticketingError.setCode(CustomMessage.NO_HANDLING.getCode());
        ticketingError.setMessage(CustomMessage.NO_HANDLING.getMessage());

        CustomeResponse response=new CustomeResponse();
        response.setData(error);
        response.setSuccess(false);
        response.setError(ticketingError);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String msg = "No handler found for " + ex.getMethod() + " " + ex.getMessage();
        TicketingError error=new TicketingError();
        error.setCode("ERR-"+ex.getMethod());
        error.setMessage(msg);
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(
                " method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        CustomError apiError = new CustomError();
        /*CustomError apiError = new CustomError(HttpStatus.METHOD_NOT_ALLOWED,
                ex.getLocalizedMessage(), builder.toString());*/
        TicketingError ticketingError=new TicketingError();
        ticketingError.setCode(CustomMessage.UNSUPPORTED_METHOD.getCode());
        ticketingError.setMessage(CustomMessage.UNSUPPORTED_METHOD.getMessage());
        CustomeResponse response=new CustomeResponse();
        response.setData(error);
        response.setSuccess(false);
        response.setError(ticketingError);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));
        String msg = "media type is not supported. Supported media types are ";
        TicketingError error =new TicketingError();
        error.setCode("ERR-"+ex.getSupportedMediaTypes());
        error.setMessage(msg);

        CustomError apiError = new CustomError();
       /* CustomError apiError = new CustomError(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));*/
        TicketingError ticketingError=new TicketingError();
        ticketingError.setCode(CustomMessage.UNSUPPORTED_MEDIA_TYPE.getCode());
        ticketingError.setMessage(CustomMessage.UNSUPPORTED_MEDIA_TYPE.getMessage());
        CustomeResponse response=new CustomeResponse();
        response.setData(error);
        response.setSuccess(false);
        response.setError(ticketingError);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
