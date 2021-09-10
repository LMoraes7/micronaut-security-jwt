package com.example.security.config;

import com.example.domain.exception.InvalidCredentials;
import com.example.domain.exception.UserNotFoundException;
import com.example.security.service.MyAuthenticationService;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Singleton
public class MyAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MyAuthenticationService authenticationService;

    public MyAuthenticationProvider(MyAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest,
                                                          AuthenticationRequest<?, ?> authenticationRequest) {
        return Flux.create(emitter -> {
            this.logger.info("starting user authentication process for username -> " + (String) authenticationRequest.getIdentity());
            try {
                this.authenticationService.authenticate(authenticationRequest);
                emitter.next(AuthenticationResponse.success((String) authenticationRequest.getIdentity()));
                emitter.complete();
            } catch (UserNotFoundException | InvalidCredentials ex) {
                this.logger.error(ex.getMessage());
                emitter.error(AuthenticationResponse.exception());
            }
            this.logger.info("ending user authentication process for username -> " + (String) authenticationRequest.getIdentity());
        }, FluxSink.OverflowStrategy.ERROR);
    }
}
