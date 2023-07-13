package com.pedro.spring.util;

import com.pedro.spring.enums.Sexo;
import com.pedro.spring.request.PersonPostRequest;
import com.pedro.spring.request.PersonPutRequest;

import java.util.UUID;

public class PersonPutBodyRequestCreated {

    public static PersonPutRequest createPersonPutBodyRequest(){
        return new PersonPutRequest(UUID.fromString("5d3046b4-2026-11ee-be56-0242ac120002"),"Pedro Teste", Sexo.M);
    }
    public static PersonPutRequest createPersonPutBodyRequest(UUID id){
        return new PersonPutRequest(id,"Pedro Teste", Sexo.M);
    }
}
