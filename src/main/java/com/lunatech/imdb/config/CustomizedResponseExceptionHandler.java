package com.lunatech.imdb.config;

import com.lunatech.imdb.exceptions.InvalidRequestException;
import com.lunatech.imdb.exceptions.ProcessingException;
import com.lunatech.imdb.exceptions.ResourceNotFoundException;
import com.lunatech.imdb.util.ApiResponseUtil;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Level;

@Log
@ControllerAdvice
@RestController
public class CustomizedResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity handleInvalidRequestException(InvalidRequestException e) {
        return ApiResponseUtil.errorResponse(HttpStatus.BAD_REQUEST, e.getErrorMessage());
    }

    @ExceptionHandler(ProcessingException.class)
    public ResponseEntity handleProcessingException(ProcessingException e) {
        return ApiResponseUtil.errorResponse(HttpStatus.NOT_MODIFIED, e.getErrorMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFoundException(ResourceNotFoundException e) {
        return ApiResponseUtil.errorResponse(HttpStatus.NOT_MODIFIED, e.getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        log.log(Level.SEVERE, "Exception: ", e);
        return ApiResponseUtil.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,String.format("An unknown error has occurred: %s", e.getMessage()));
    }
}
