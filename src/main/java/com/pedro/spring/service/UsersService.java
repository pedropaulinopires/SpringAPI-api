package com.pedro.spring.service;

import com.pedro.spring.domain.Users;
import com.pedro.spring.exception.UserException;
import com.pedro.spring.repository.UsersRepository;
import com.pedro.spring.request.UserLoginRequest;
import com.pedro.spring.request.UsersPostRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    private final JwtService jwtService;

    public static final String KEY = "UserAuthenticated";

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public Users saveUsers(UsersPostRequest user, HttpServletResponse response) throws UnsupportedEncodingException {
        Users usersFindUsername = usersRepository.usersFindByUsername(user.getUsername());
        if (usersFindUsername == null) {
            user.setPassword(passwordEncoder().encode(user.getPassword()));
            Users userSave = usersRepository.save(user.build());
            String value = jwtService.generatedToken(userSave);
            CookieService.setCookie(response,KEY,value,60*60*24);
            return userSave;
        }
        throw new UserException("Not save user, username in use!");
    }

    public boolean loginUser(UserLoginRequest user, HttpServletResponse response) throws UnsupportedEncodingException {
        Users usersFindUsername = usersRepository.usersFindByUsername(user.getUsername());
        if (usersFindUsername != null && passwordEncoder().matches(user.getPassword(), usersFindUsername.getPassword())) {
            String value = jwtService.generatedToken(usersFindUsername);
            CookieService.setCookie(response,KEY,value,60*60*24);
            return true;
        } else {
            throw new UserException("Username/password invalid!");
        }
    }

    public boolean checkUser(HttpServletRequest request){
        try{
            String cookie = CookieService.getCookie(request, KEY);
            if(cookie != null ){
                String tokenValid = jwtService.readToken(cookie);
                Optional<Users> userSearch = usersRepository.findById(UUID.fromString(tokenValid));
                if(userSearch != null){
                    return true;
                }
                return false;
            }
        } catch (Exception e){
            return false;
        }
        return false;

    }
}
