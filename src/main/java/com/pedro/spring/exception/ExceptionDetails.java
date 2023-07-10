package com.pedro.spring.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class ExceptionDetails {

    protected LocalDateTime timestamp;
    protected int status;
    protected String error;
    protected String message;
    protected String messageDeveloper;
}
