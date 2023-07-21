package com.pedro.spring.integration;

import com.pedro.spring.domain.Person;
import com.pedro.spring.enums.Authorities;
import com.pedro.spring.repository.PersonRepository;
import com.pedro.spring.repository.UsersRepository;
import com.pedro.spring.request.UsersPostRequest;
import com.pedro.spring.util.PersonCreated;
import com.pedro.spring.util.PersonPostBodyRequestCreated;
import com.pedro.spring.util.PersonPutBodyRequestCreated;
import com.pedro.spring.wrapper.PageableResponse;
import lombok.extern.log4j.Log4j2;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Log4j2
public class PersonControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UsersRepository usersRepository;

    private Person savePerson() {
        return personRepository.save(PersonCreated.createPersonToBeSave());
    }

    private HttpHeaders tokenUserAuth() {
        ResponseEntity<Void> exchange = testRestTemplate.exchange("/user/save", HttpMethod.POST, new HttpEntity<>
                (new UsersPostRequest("teste92470", "teste92470", List.of(Authorities.ROLE_USER, Authorities.ROLE_ADMIN))), Void.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", exchange.getHeaders().getFirst("Set-Cookie"));
        return headers;
    }

    @Test
    @DisplayName("Get find all peoples when success full!")
    void get_findAllPeoples_WhenSuccessFull() {
        HttpHeaders headers = tokenUserAuth();
        Person personSave = savePerson();
        PageableResponse<Person> pagePerson = testRestTemplate.exchange("/peoples?page=0", HttpMethod.GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<PageableResponse<Person>>() {
        }).getBody();
        Assertions.assertThat(pagePerson).isNotNull().hasSize(1);
        Assertions.assertThat(pagePerson.stream().toList().get(0)).isEqualTo(personSave);
    }

    @Test
    @DisplayName("Get find person by id when success full!")
    void get_findPersonById_WhenSuccessFull() {
        HttpHeaders headers = tokenUserAuth();
        Person personSave = savePerson();
        Person searchPersonById = testRestTemplate.exchange("/people/{id}", HttpMethod.GET, new HttpEntity<>(null, headers), Person.class, personSave.getId()).getBody();
        Assertions.assertThat(searchPersonById).isNotNull();
        Assertions.assertThat(searchPersonById).isEqualTo(personSave);
    }

    @Test
    @DisplayName("Post save person when success full!")
    void post_savePerson_WhenSuccessFull() {
        HttpHeaders headers = tokenUserAuth();
        Person personSave = testRestTemplate.exchange("/people/save", HttpMethod.POST, new HttpEntity<>(PersonPostBodyRequestCreated.createPersonPostBodyRequest(), headers), Person.class).getBody();
        Assertions.assertThat(personSave).isNotNull();
    }

    @Test
    @DisplayName("Delete remove person by id when success full!")
    void delete_removePersonById_WhenSuccessFull() {
        HttpHeaders headers = tokenUserAuth();
        Person personSave = savePerson();
        Person searchPersonById = testRestTemplate.exchange("/people/{id}", HttpMethod.DELETE, new HttpEntity<>(null, headers), Person.class, personSave.getId()).getBody();
        Assertions.assertThat(searchPersonById).isNull();
    }

    @Test
    @DisplayName("Put replace person when success full!")
    void put_replacePerson_WhenSuccessFull() {
        HttpHeaders headers = tokenUserAuth();
        Person personSave = savePerson();
        testRestTemplate.exchange("/people/replace", HttpMethod.PUT, new HttpEntity<>(PersonPutBodyRequestCreated.createPersonPutBodyRequest(personSave.getId()), headers), Void.class);
        Page<Person> pagePerson = testRestTemplate.exchange("/peoples?page=0", HttpMethod.GET, new HttpEntity<>(null, headers), new ParameterizedTypeReference<PageableResponse<Person>>() {
        }).getBody();

        Assertions.assertThat(pagePerson).isNotNull().isNotEqualTo(personSave);
    }
}
