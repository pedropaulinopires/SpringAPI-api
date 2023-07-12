package com.pedro.spring.repository;

import com.pedro.spring.domain.Person;
import com.pedro.spring.enums.Sexo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    //create entity
    private Person createPerson(){
        return new Person(null,"Pedro", Sexo.M);
    }

    private Person savePerson(){
        return  personRepository.save(createPerson());
    }


    @Test
    @DisplayName("List pageable find all peoples when success full!")
    void  list_findPeoplesPageable_WhenSuccessFull(){
        Person personSave = savePerson();
        List<Person> listPeoples = personRepository.findAll();
        Assertions.assertThat(listPeoples).isNotNull().hasSize(1);
        Assertions.assertThat(listPeoples.get(0)).isEqualTo(personSave);

    }

    @Test
    @DisplayName("Get find person by id when success full!")
    void  get_findPersonById_WhenSuccessFull(){
        Person personSave = savePerson();
        Person searchPersonById  = personRepository.findById(personSave.getId()).get();
        Assertions.assertThat(searchPersonById).isNotNull();
        Assertions.assertThat(searchPersonById).isEqualTo(personSave);
    }

    @Test
    @DisplayName("Post save person when success full!")
    void  post_savePerson_WhenSuccessFull(){
        Person personSave = savePerson();
        List<Person> listPeoples = personRepository.findAll();
        Assertions.assertThat(listPeoples).isNotNull().hasSize(1);
        Assertions.assertThat(listPeoples.get(0)).isEqualTo(personSave);
    }

    @Test
    @DisplayName("Put replace person when success full!")
    void  put_replacePerson_WhenSuccessFull(){
        Person personSave = savePerson();
        personSave.setName("Pedro Teste");
        Person personReplace = personRepository.save(personSave);
        List<Person> listPeoples = personRepository.findAll();
        Assertions.assertThat(listPeoples).isNotNull().hasSize(1);
        Assertions.assertThat(listPeoples.get(0)).isEqualTo(personReplace);
    }

    @Test
    @DisplayName("Delete remove person by id when success full!")
    void  delete_removePersonById_WhenSuccessFull(){
        Person personSave = savePerson();
        List<Person> listPeoples = personRepository.findAll();
        Assertions.assertThat(listPeoples).isNotNull().hasSize(1);
        Assertions.assertThat(listPeoples.get(0)).isEqualTo(personSave);
        //delete person
        personRepository.deleteById(personSave.getId());
        listPeoples = personRepository.findAll();
        Assertions.assertThat(listPeoples).isNotNull().hasSize(0);
    }

}