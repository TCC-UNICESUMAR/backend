package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.AuthenticationRequest;
import com.br.tcc.bfn.dtos.AuthenticationResponse;
import com.br.tcc.bfn.dtos.ProductDto;
import com.br.tcc.bfn.dtos.Response;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.impl.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	private final AuthenticationService service;

	private final UserRepository repository;

	private final static Logger LOGGER = Logger.getLogger(AuthenticationController.class.getName());


	public AuthenticationController(AuthenticationService service, UserRepository repository) {
		super();
		this.service = service;
		this.repository = repository;
	}


	@PostMapping("/authenticate")
	public ResponseEntity authenticate(@RequestBody AuthenticationRequest request) {
		try {
			return ResponseEntity.ok(service.authenticate(request));
		}catch (Exception e){
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<Response<AuthenticationResponse>> getRefreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
		Response<AuthenticationResponse> dtoResponse = new Response<>();
		try{
			dtoResponse.setData(service.refreshToken(authorization));
			dtoResponse.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
		}catch (Exception e){
			dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			dtoResponse.setError(e.getMessage());
			return ResponseEntity.badRequest().body(dtoResponse);
		}
	}


}
