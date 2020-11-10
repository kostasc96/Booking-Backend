package gr.dit.tenants.controllers;

import gr.dit.tenants.exceptions.BadRequestException;
import gr.dit.tenants.exceptions.ResourceNotFoundException;
import gr.dit.tenants.view.ResponseStatus;
import gr.dit.tenants.view.ResponseView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseView> handleGenericException(Exception ex)
    {

        return new ResponseEntity<>(new ResponseView(
                ResponseStatus.INTERNAL_SERVER_ERROR,
                "An unknown error has occurred."),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //TODO BadCredentialsException
    //TODO HttpMessageNotReadableException


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseView> ResourceNotFoundException(ResourceNotFoundException ex)
    {
        return new ResponseEntity<>(new ResponseView(
                ResponseStatus.RESOURCE_NOT_FOUND,
                "Could not find your requested entity. See details for more info", ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseView> handleBadRequestException(BadRequestException ex)
    {
        return new ResponseEntity<>(new ResponseView(
                ResponseStatus.FAILED_OPERATION,
                "Your request could not be fulfilled. See details for more info",
                ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    //Exception controller when @Valid fails
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseView> handleValidationException(MethodArgumentNotValidException ex)
    {
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(x-> x.getDefaultMessage())
                .collect(Collectors.toList());


        return new ResponseEntity<>(new ResponseView(
                ResponseStatus.VALIDATION_FAILED,
                "Constraint violation detected",errors),
                HttpStatus.BAD_REQUEST);
    }

}
