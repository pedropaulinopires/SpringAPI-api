package com.pedro.spring.exception;

import lombok.Getter;

@Getter
public class PersonNotFoundById extends RuntimeException {

    public PersonNotFoundById(String message) {
        super(message);
    }
}
