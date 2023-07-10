package com.pedro.spring.service;

import com.pedro.spring.domain.Person;
import com.pedro.spring.exception.PersonNotFoundById;
import com.pedro.spring.repository.PersonRepository;
import com.pedro.spring.request.PersonPostRequest;
import com.pedro.spring.request.PersonPutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person savePerson(PersonPostRequest person) {
        return personRepository.save(person.build());
    }

    public Page<Person> findAll(PageRequest pageRequest) {
        return personRepository.findAll(pageRequest);
    }


    public Person findById(String id) {
        return personRepository.findById(UUID.fromString(id)).orElseThrow(() -> new PersonNotFoundById("Person not found by id"));
    }

    public void replacePerson(PersonPutRequest person) {
        personRepository.save(person.build());
    }

    public void deleteById(String id) {
        personRepository.delete(findById(id));
    }


}
