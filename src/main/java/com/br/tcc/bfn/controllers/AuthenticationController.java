package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dto.AuthenticationRequest;
import com.br.tcc.bfn.dto.RegisterRequest;
import com.br.tcc.bfn.dto.UserDTO;
import com.br.tcc.bfn.model.User;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	private final AuthenticationService service;

	private final UserRepository repository;

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
