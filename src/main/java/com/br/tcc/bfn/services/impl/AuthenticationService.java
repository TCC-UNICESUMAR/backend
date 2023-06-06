package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.dtos.*;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.utils.BfnConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, JwtService jwtService, AuthenticationManager authenticationManager) {
        super();
        this.repository = repository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Response<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        Response<AuthenticationResponse> response = new Response<>();
        try {
            User user = repository.findByEmail(request.getEmail()).get();
            if (user == null) {
                throw new UsernameNotFoundException("User Not Found");
            }
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtService.generateToken(user), jwtService.refreshToken(user));
            response.setData(authenticationResponse);
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        } catch (UsernameNotFoundException e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            response.setError(e.getMessage());
            return response;
        }
    }

    public AuthenticationResponse refreshToken(final String authorization) {
        try {
            String token = Optional
                    .ofNullable(authorization)
                    .map(it -> it.replace("Bearer ", ""))
                    .orElse("");

            if (!jwtService.isTokenExpired(token)) {
                throw new JwtException(BfnConstants.TOKEN_IS_VALID);
            }

            final String username = jwtService.extractUsername(token);
            final User user = repository.findByEmail(username).get();
            return new AuthenticationResponse(jwtService.generateToken(user), jwtService.refreshToken(user));
        }catch (JwtException exception){
            throw new JwtException(exception.getMessage());
        }
    }
}
