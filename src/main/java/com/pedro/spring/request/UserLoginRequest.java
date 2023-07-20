package com.pedro.spring.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest {

    @NotEmpty(message = "field username is not empty!")
    private String username;

    @NotEmpty(message = "field password is not empty!")
    private String password;
}
