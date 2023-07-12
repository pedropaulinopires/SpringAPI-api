package com.pedro.spring.service;

import com.pedro.spring.domain.Person;
import com.pedro.spring.repository.PersonRepository;
import com.pedro.spring.util.PersonCreated;
import com.pedro.spring.util.PersonPostBodyRequestCreated;
import com.pedro.spring.util.PersonPutBodyRequestCreated;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(value = SpringExtension.class)
@AutoConfigureTestDatabase
class PersonServiceTest {

    //inject service
    @InjectMocks
    private PersonService personService;


    //inject repository use in service
    @Mock
    private PersonRepository personRepository;

    private final String UUID_PERSON = "5d3046b4-2026-11ee-be56-0242ac120002";

    @BeforeEach
    void setUp() {

        ///pageable person
        PageImpl<Person> peoples = new PageImpl<>(List.of(PersonCreated.createPersonToBeValid()));
        //////////////////////////////////////////////////////////////////

        //find all
        BDDMockito.when(personRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(peoples);
        ///////////////////////////////////////////////////////////////////

        //get by id
        BDDMockito.when(personRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(PersonCreated.createPersonToBeValid()));
        ////////////////////////////////////////////////////////////////////

        //save person
        BDDMockito.when(personRepository.save(ArgumentMatchers.any(Person.class))).thenReturn(PersonCreated.createPersonToBeValid());
        ////////////////////////////////////////////////////////////////////

        //delete by id person
        BDDMockito.doNothing().when(personRepository).deleteById(ArgumentMatchers.any(UUID.class));
        ////////////////////////////////////////////////////////////////////

    }

    private Person createPerson() {
        return personService.savePerson(PersonPostBodyRequestCreated.createPersonPostBodyRequest());
    }

    @Test
    @DisplayName("List pageable find all peoples when success full!")
    void list_findAllPeoplesPageable_WhenSuccessFull() {
        Person personSave = createPerson();
        Page<Person> pagePeoples = personService.findAll(PageRequest.of(0, 1, Sort.by("name")));
        Assertions.assertThat(pagePeoples).isNotEmpty().hasSize(1);
        Assertions.assertThat(pagePeoples.toList().get(0)).isEqualTo(personSave);
    }


    @Test
    @DisplayName("Get find person by id when success full!")
    void get_findPersonById_WhenSuccessFull() {
        Person personSave = createPerson();
        Person searchPersonById = personService.findById(UUID_PERSON);
        Assertions.assertThat(searchPersonById).isNotNull();
        Assertions.assertThat(searchPersonById).isEqualTo(PersonCreated.createPersonToBeValid());
        Assertions.assertThat(searchPersonById.getId()).isEqualTo(UUID.fromString(UUID_PERSON));
    }

    @Test
    @DisplayName("Save person when success full!")
    void post_savePerson_WhenSuccessFull() {
        Person personSave = createPerson();
        Page<Person> pagePeoples = personService.findAll(PageRequest.of(0, 1, Sort.by("name")));
        Assertions.assertThat(pagePeoples).isNotEmpty().isNotNull();
        Assertions.assertThat(pagePeoples.stream().toList()).isEqualTo(List.of(PersonCreated.createPersonToBeValid()));
        Assertions.assertThat(pagePeoples.stream().toList().get(0).getId()).isEqualTo(PersonCreated.createPersonToBeValid().getId());
        Assertions.assertThat(pagePeoples.stream().toList().get(0).getName()).isEqualTo(PersonCreated.createPersonToBeValid().getName());
    }

    @Test
    @DisplayName("Put replace person when success full!")
    void put_replacePerson_WhenSuccessFull() {
        Assertions.assertThatCode(() -> personService.replacePerson(PersonPutBodyRequestCreated.createPersonPutBodyRequest())).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Delete remove person by id when success full!")
    void delete_removePersonById_WhenSuccessFull() {
        Assertions.assertThatCode(() -> personService.deleteById(UUID_PERSON)).doesNotThrowAnyException();
    }


}