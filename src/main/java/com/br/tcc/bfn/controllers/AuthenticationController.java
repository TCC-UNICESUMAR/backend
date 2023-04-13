package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.AuthenticationRequest;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
