package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.dtos.AuthenticationRequest;
import com.br.tcc.bfn.dtos.AuthenticationResponse;
import com.br.tcc.bfn.dtos.Response;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.utils.BfnConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class.getName());

    public Response<AuthenticationResponse> authenticate(AuthenticationRequest request, HttpServletRequest httpServletRequest) {
        Response<AuthenticationResponse> response = new Response<>();
        try {
            User user = repository.findByEmail(request.getEmail()).get();
            if (user == null) {
                throw new UsernameNotFoundException("User Not Found");
            }
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            httpServletRequest.getSession().setAttribute(user.getUsername(), user);
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("roles", user.getAuthorities());
            extraClaims.put("UF", user.getAddress().getState().getUf());
            AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtService.generateToken(user,extraClaims), jwtService.refreshToken(user));
            response.setBody(authenticationResponse);
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        } catch (UsernameNotFoundException e) {
            LOGGER.error("Error on method authenticate -> " + e.getMessage());
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
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("roles", user.getAuthorities());
            extraClaims.put("UF", user.getAddress().getState().getUf());
            return new AuthenticationResponse(jwtService.generateToken(user,extraClaims), jwtService.refreshToken(user));
        }catch (JwtException exception){
            throw new JwtException(exception.getMessage());
        }
    }
}
