package com.pedro.spring.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationException extends ExceptionDetails {

    protected String fields;

    protected String fieldsMessage;
}
