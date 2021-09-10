package com.example.security.service;

import com.example.domain.exception.InvalidCredentials;
import com.example.domain.exception.UserNotFoundException;
import com.example.domain.model.User;
import com.example.domain.repository.UserRepository;
import com.example.security.config.MyPasswordEncoder;
import io.micronaut.security.authentication.AuthenticationRequest;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MyAuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserRepository repository;
    private MyPasswordEncoder encoder;

    public MyAuthenticationService(UserRepository repository, MyPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public void authenticate(AuthenticationRequest<?, ?> authenticationRequest) {
        String usernameReceived = (String) authenticationRequest.getIdentity();
        String passwordReceived = (String) authenticationRequest.getSecret();

        User usuario = this.repository.findByUsername(usernameReceived)
                .orElseThrow(() -> new UserNotFoundException("the username " + usernameReceived + " does not exist"));

        if (!this.encoder.matches(passwordReceived, usuario.getPassword()))
            throw new InvalidCredentials("authentication credentials entered are invalid for username " + usernameReceived);
        this.logger.info("authenticating user -> " + usernameReceived);
    }
}
