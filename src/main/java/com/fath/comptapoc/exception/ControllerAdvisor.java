package com.fath.comptapoc.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;

@ControllerAdvice
public class ControllerAdvisor {

   @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid (MethodArgumentNotValidException e) {

       Map<String, Object> body = new LinkedHashMap<>();
       List<String> errors = e.getBindingResult()
               .getFieldErrors()
               .stream()
               .map( DefaultMessageSourceResolvable::getDefaultMessage )
               .collect(Collectors.toList());

       body.put("message", errors);

         return new ResponseEntity<>( body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( ClassNotFoundException.class )
    protected ResponseEntity<Object> handleNotFoundException( ClassNotFoundException e ){
        return new ResponseEntity<>( e.getMessage(), HttpStatus.NOT_FOUND );
    }

    @ExceptionHandler( Exception.class )
    protected ResponseEntity<Object> handleException (Exception e ){
       return new ResponseEntity<>( e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR );
    }
}
