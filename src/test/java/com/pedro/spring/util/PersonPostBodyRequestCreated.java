package com.pedro.spring.util;

import com.pedro.spring.enums.Sexo;
import com.pedro.spring.request.PersonPostRequest;

public class PersonPostBodyRequestCreated {

    public static PersonPostRequest createPersonPostBodyRequest(){
        return new PersonPostRequest("Pedro Teste", Sexo.M);
    }
}
