package com.pedro.spring.request;

import com.pedro.spring.domain.Users;
import com.pedro.spring.enums.Authorities;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsersPostRequest {

    @NotEmpty( message = "field username not empty")
    private String username;

    @NotEmpty( message = "field password not empty")
    private String password;

    @NotEmpty( message = "field Authorities not empty")
    private List<Authorities> authoritiesList;

    public Users build() {
        return new Users().builder()
                .username(this.username)
                .password(this.password)
                .authoritiesList(this.authoritiesList)
                .build();
    }
}
