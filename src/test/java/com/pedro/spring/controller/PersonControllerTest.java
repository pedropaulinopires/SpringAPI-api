package com.pedro.spring.controller;

import com.pedro.spring.domain.Person;
import com.pedro.spring.request.PersonPostRequest;
import com.pedro.spring.service.PersonService;
import com.pedro.spring.util.PersonCreated;
import com.pedro.spring.util.PersonPostBodyRequestCreated;
import com.pedro.spring.util.PersonPutBodyRequestCreated;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(value = SpringExtension.class)
@AutoConfigureTestDatabase
@Log4j2
class PersonControllerTest {

    //inject controller
    @InjectMocks
    private PersonController personController;


    //inject dependency in use controller
    @Mock
    private PersonService personService;

    private final String UUID_PERSON = "5d3046b4-2026-11ee-be56-0242ac120002";

    @BeforeEach
    void setUp() {

        // pageable person
        Page<Person> pagePerson = new PageImpl<>(List.of(PersonCreated.createPersonToBeValid()));
        ///////////////////////////////////////////////////////////////

        //find all pageable person
        BDDMockito.when(personService.findAll(ArgumentMatchers.any())).thenReturn(pagePerson);
        ///////////////////////////////////////////////////////////////////////////

        //find person by id
        BDDMockito.when(personService.findById(ArgumentMatchers.any())).thenReturn(PersonCreated.createPersonToBeValid());
        /////////////////////////////////////////////////////////////////

        //save person
        BDDMockito.when(personService.savePerson(ArgumentMatchers.any(PersonPostRequest.class))).thenReturn(PersonCreated.createPersonToBeValid());
        //////////////////////////////////////////////////////////////////

        //delete person
        BDDMockito.doNothing().when(personService).deleteById(UUID_PERSON);
        //////////////////////////////////////////////////////////////////


        //replace person
        BDDMockito.doNothing().when(personService).replacePerson(PersonPutBodyRequestCreated.createPersonPutBodyRequest());
        //////////////////////////////////////////////////////////////////

    }

    @Test
    @DisplayName("Get find all peoples pageable when success full!")
    void get_findAllPeoplesPageable_WhenSuccessFull() {
        Page<Person> pagePerson = personController.findAll(Optional.of(1)).getBody();
        Assertions.assertThat(pagePerson.stream().toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(pagePerson.stream().toList().get(0)).isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Get find person by id when success full!")
    void get_findPersonById_WhenSuccessFull() {
        Person searchPersonById = personController.find(UUID_PERSON).getBody();
        Assertions.assertThat(searchPersonById).isNotNull().isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Post save person when success full!")
    void post_savePerson_WhenSuccessFull() {
        Person personSave = personController.save(PersonPostBodyRequestCreated.createPersonPostBodyRequest()).getBody();
        Assertions.assertThat(personSave).isNotNull().isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Delete remove person by id when success full!")
    void delete_removePersonById_WhenSuccessFull() {
        Assertions.assertThatCode(() -> personController.delete(UUID_PERSON)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Put replace person when success full!")
    void put_replacePerson_WhenSuccessFull() {
        Assertions.assertThatCode(() -> personController.replace(PersonPutBodyRequestCreated.createPersonPutBodyRequest())).doesNotThrowAnyException();
    }


}