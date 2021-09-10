package com.example.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/hello")
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(Principal principal) {
        this.logger.info("receiving a request from user " + principal.getName() + " for HelloWorldController");
        return "Hello World " + principal.getName();
    }
}
