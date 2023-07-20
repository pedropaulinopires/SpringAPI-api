package com.pedro.spring.repository;

import com.pedro.spring.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    @Query("SELECT u FROM Users u where u.username = :username")
    Users usersFindByUsername(@Param("username") String username);
}
