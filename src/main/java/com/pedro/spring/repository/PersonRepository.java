package com.pedro.spring.repository;

import com.pedro.spring.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository  extends JpaRepository<Person, UUID> {

}
