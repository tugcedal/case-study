package com.example.stockexchange.controller;

import com.company.project.model.ErrorResponse;
import com.example.stockexchange.domain.exception.ResourceAlreadyExists;
import com.example.stockexchange.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

import static com.example.stockexchange.utils.Constants.Error.*;

@Controller
public class BaseController {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundExceptions(ResourceNotFoundException e) {
        ErrorResponse responseError = buildErrorResponse(HttpStatus.BAD_REQUEST, e, RESOURCE_NOT_FOUND);
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExceptions(ResourceAlreadyExists e) {
        ErrorResponse responseError = buildErrorResponse(HttpStatus.BAD_REQUEST, e, RESOURCE_ALREADY_EXISTS);

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleTechnicalExceptions(RuntimeException e) {
        ErrorResponse responseError = buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e, TECHNICAL_ERROR);
        return new ResponseEntity<>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ErrorResponse buildErrorResponse(HttpStatusCode statusCode, Exception exception, String error) {
        ErrorResponse responseError = new ErrorResponse();
        responseError.status(statusCode.value());
        responseError.setMessage(exception.getMessage());
        responseError.setError(error);
        responseError.setTimestamp(new Date());

        return responseError;
    }
}
