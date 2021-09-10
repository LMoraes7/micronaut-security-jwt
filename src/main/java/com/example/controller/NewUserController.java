package com.example.controller;

import com.example.controller.request.NewUserRequest;
import com.example.domain.model.User;
import com.example.domain.repository.UserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller("/usuarios")
@Validated
public class NewUserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private UserRepository repository;

    public NewUserController(UserRepository repository) {
        this.repository = repository;
    }

    @Post
    @PermitAll
    @Transactional
    public HttpResponse<?> register(@Body @Valid NewUserRequest request) {
        this.logger.info("receiving a request for NewUserController -> " + request);
        this.logger.info("saving new user -> " + request);
        var user = this.repository.save(request.toDomain());
        this.logger.info("performed user registration");
        return HttpResponse.status(HttpStatus.CREATED);
    }
}
