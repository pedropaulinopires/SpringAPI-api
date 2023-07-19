package com.pedro.spring.service;

import com.pedro.spring.domain.Users;
import com.pedro.spring.repository.UsersRepository;
import com.pedro.spring.request.UsersPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public Users saveUsers(UsersPostRequest user){
        return null;
    }
}
