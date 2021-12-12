package br.com.treinaweb.personalexpensesmanager.api.v1.handlers;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.ErrorResponse;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.FieldErrorResponse;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerEntityNotFoundException(
        EntityNotFoundException exception, HttpServletRequest request
    ) {
        var status = HttpStatus.NOT_FOUND;

        var errorResponse = ErrorResponse.builder()
            .status(status.value())
            .error(status.getReasonPhrase())
            .message(exception.getLocalizedMessage())
            .path(request.getRequestURI())
            .timestamp(LocalDateTime.now())
            .build();

        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException exception,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        var validationErrors = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> new FieldErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()))
            .toList();
        var errorResponse = ErrorResponse.builder()
            .status(status.value())
            .error(status.getReasonPhrase())
            .message("One or more validation errors were detected")
            .path(((ServletWebRequest)request).getRequest().getRequestURI())
            .timestamp(LocalDateTime.now())
            .validationErrors(validationErrors)
            .build();
        return new ResponseEntity<>(errorResponse, status);
    }

}
