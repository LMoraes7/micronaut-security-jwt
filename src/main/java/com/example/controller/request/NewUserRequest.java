package com.example.controller.request;

import com.example.domain.model.User;
import io.micronaut.core.annotation.Introspected;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Introspected
public class NewUserRequest {

    @NotBlank
    @Size(min = 5, max = 10)
    private String username;
    @NotBlank
    @Size(min = 7, max = 10)
    private String password;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User toDomain() {
        return new User(this.username, new BCryptPasswordEncoder().encode(this.password));
    }

    @Override
    public String toString() {
        return "NewUserRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}