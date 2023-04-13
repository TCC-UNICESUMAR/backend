package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.*;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
			if(user == null){
				throw new UsernameNotFoundException("User Not Found");
			}
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtService.generateToken(user), jwtService.refreshToken(user));
			response.setData(authenticationResponse);
			response.setStatusCode(HttpStatus.OK.value());
			return response;
		}catch (UsernameNotFoundException e){
			response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
			response.setError(e.getMessage());
			return response;
		}
	}

}
