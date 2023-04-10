package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.config.JwtService;
import com.br.tcc.bfn.controllers.TicketController;
import com.br.tcc.bfn.dto.AuthenticationRequest;
import com.br.tcc.bfn.dto.Response;
import com.br.tcc.bfn.model.User;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.IAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final static Logger LOGGER = Logger.getLogger(AuthenticationServiceImpl.class.getName());
    public AuthenticationServiceImpl(UserRepository repository, JwtService jwtService, AuthenticationManager authenticationManager) {
        super();
        this.repository = repository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Response<String> authenticate(AuthenticationRequest request) {
        Response<String> response = new Response<>();
        try {
            User user = repository.findByEmail(request.getEmail()).get();
            if(user == null){
                throw new UsernameNotFoundException("User Not Found");
            }
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            final String acessToken = jwtService.generateToken(user);


            response.setData(acessToken);
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        }catch (UsernameNotFoundException e){
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            response.setError(e.getMessage());
            return response;
        }
    }
}
