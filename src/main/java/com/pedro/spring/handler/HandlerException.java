package com.pedro.spring.handler;

import com.pedro.spring.exception.ExceptionDetails;
import com.pedro.spring.exception.PersonNotFoundById;
import com.pedro.spring.exception.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    private static LocalDateTime TIME = LocalDateTime.now();

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDetails> dataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .timestamp(TIME)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(ex.getMessage())
                .message(ex.getMessage())
                .messageDeveloper(ex.getLocalizedMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PersonNotFoundById.class)
    public ResponseEntity<ExceptionDetails> handlerPersonNotFoundById(PersonNotFoundById exception) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .timestamp(TIME)
                        .status(HttpStatus.NO_CONTENT.value())
                        .error("Person not found by id")
                        .message(exception.getMessage())
                        .messageDeveloper(exception.getClass() + " // " + exception.getLocalizedMessage() + "// " + TIME)
                        .build()
                , HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception
            , HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        return new ResponseEntity<>(
                ValidationException.builder()
                        .timestamp(TIME)
                        .status(status.value())
                        .error("Field[s] invalid")
                        .message(exception.getMessage())
                        .messageDeveloper(exception.getClass() + " // " + exception.getLocalizedMessage() + "// " + TIME)
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build()
                , status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers
            , HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .timestamp(TIME)
                .status(status.value())
                .error(ex.getCause().toString())
                .message(ex.getMessage())
                .messageDeveloper(ex.getClass().getName())
                .build(), status);
    }
}
