package com.pedro.spring.controller;

import com.pedro.spring.domain.Users;
import com.pedro.spring.request.UserLoginRequest;
import com.pedro.spring.request.UsersPostRequest;
import com.pedro.spring.service.UsersService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/user/save")
    public ResponseEntity<Users> saveUser(@Valid @RequestBody UsersPostRequest user, HttpServletResponse response) throws UnsupportedEncodingException {
        Users userSave = usersService.saveUsers(user, response);
        if (userSave != null) {
            return new ResponseEntity<>(userSave, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }

    @PostMapping("/user/auth")
    public ResponseEntity<Void> loginUser(@Valid @RequestBody UserLoginRequest user, HttpServletResponse response) throws UnsupportedEncodingException {
        String numS = "159,99";
        float num = Float.parseFloat(numS.replace(",","."));
        System.out.println(num);
        if (usersService.loginUser(user, response)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
