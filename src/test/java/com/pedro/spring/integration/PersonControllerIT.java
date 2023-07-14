package com.pedro.spring.integration;

import com.pedro.spring.domain.Person;
import com.pedro.spring.repository.PersonRepository;
import com.pedro.spring.util.PersonCreated;
import com.pedro.spring.util.PersonPostBodyRequestCreated;
import com.pedro.spring.util.PersonPutBodyRequestCreated;
import com.pedro.spring.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PersonRepository personRepository;

    private Person savePerson() {
        return personRepository.save(PersonCreated.createPersonToBeSave());
    }

    @Test
    @DisplayName("Get find all peoples when success full!")
    void get_findAllPeoples_WhenSuccessFull() {
        Person personSave = savePerson();
        Page<Person> pagePerson = testRestTemplate.exchange("/peoples?page=0", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Person>>() {
        }).getBody();
        Assertions.assertThat(pagePerson).isNotNull().hasSize(1);
        Assertions.assertThat(pagePerson.stream().toList().get(0)).isEqualTo(personSave);
    }

    @Test
    @DisplayName("Get find person by id when success full!")
    void get_findPersonById_WhenSuccessFull() {
        Person personSave = savePerson();
        Person searchPersonById = testRestTemplate.exchange("/people/{id}", HttpMethod.GET, null, Person.class, personSave.getId()).getBody();
        Assertions.assertThat(searchPersonById).isNotNull();
        Assertions.assertThat(searchPersonById).isEqualTo(personSave);
    }

    @Test
    @DisplayName("Post save person when success full!")
    void post_savePerson_WhenSuccessFull() {
        Person personSave = testRestTemplate.exchange("/people/save", HttpMethod.POST, new HttpEntity<>(PersonPostBodyRequestCreated.createPersonPostBodyRequest()), Person.class).getBody();
        Assertions.assertThat(personSave).isNotNull();
    }

    @Test
    @DisplayName("Delete remove person by id when success full!")
    void delete_removePersonById_WhenSuccessFull() {
        Person personSave = savePerson();
        Person searchPersonById = testRestTemplate.exchange("/people/{id}", HttpMethod.DELETE, null, Person.class, personSave.getId()).getBody();
        Assertions.assertThat(searchPersonById).isNull();
    }

    @Test
    @DisplayName("Put replace person when success full!")
    void put_replacePerson_WhenSuccessFull() {
        Person personSave = savePerson();
        testRestTemplate.exchange("/people/replace", HttpMethod.PUT, new HttpEntity<>(PersonPutBodyRequestCreated.createPersonPutBodyRequest(personSave.getId())), Void.class);
        Page<Person> pagePerson = testRestTemplate.exchange("/peoples?page=0", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Person>>() {
        }).getBody();

        Assertions.assertThat(pagePerson).isNotNull().isNotEqualTo(personSave);
    }
}
