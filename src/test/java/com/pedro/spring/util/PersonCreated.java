package com.pedro.spring.util;

import com.pedro.spring.domain.Person;
import com.pedro.spring.enums.Sexo;

import java.util.UUID;

public class PersonCreated {

    public static Person createPersonToBeSave(){
        return new Person(null,"Pedro Teste", Sexo.M);
    }

    public static Person createPersonToBeValid(){
        return new Person(UUID.fromString("5d3046b4-2026-11ee-be56-0242ac120002"),"Pedro Teste", Sexo.M);
    }

    public static Person createPersonToBeReplace(){
        return new Person(UUID.fromString("5d3046b4-2026-11ee-be56-0242ac120002"),"Pedro Teste Update", Sexo.M);
    }
}
