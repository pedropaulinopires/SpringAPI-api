package com.pedro.spring.controller;

import com.pedro.spring.domain.Person;
import com.pedro.spring.request.PersonPostRequest;
import com.pedro.spring.request.PersonPutRequest;
import com.pedro.spring.service.PersonService;
import com.pedro.spring.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    private final UsersService usersService;

    @GetMapping("/peoples")
    public ResponseEntity<Page<Person>> findAll(HttpServletRequest request, @Param("page") Optional<Integer> page) throws UnsupportedEncodingException {

        if (usersService.checkUser(request)) {
            int currentPage = page.orElse(1) - 1;
            if (currentPage < 0) {
                currentPage = 0;
            }
            Page<Person> peoplesPage = personService.findAll(PageRequest.of(currentPage, 6, Sort.by("name")));

            if (currentPage != 0 && currentPage >= peoplesPage.getTotalPages()) {
                currentPage = peoplesPage.getTotalPages() - 1;
                peoplesPage = personService.findAll(PageRequest.of(currentPage, 6, Sort.by("name")));
            }
            return new ResponseEntity<>(peoplesPage, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);


    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> find(@PathVariable String id) {
        return new ResponseEntity<>(personService.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/people/save")
    public ResponseEntity<Person> save(@Valid @RequestBody PersonPostRequest person) {
        return new ResponseEntity<>(personService.savePerson(person), HttpStatus.CREATED);

    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        personService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/people/replace")
    public ResponseEntity<Void> replace(@RequestBody @Valid PersonPutRequest person) {
        personService.replacePerson(person);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
