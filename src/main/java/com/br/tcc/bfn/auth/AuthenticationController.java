package com.br.tcc.bfn.auth;

import com.br.tcc.bfn.dto.UserDTO;
import com.br.tcc.bfn.model.User;
import com.br.tcc.bfn.repositories.UserRepository;
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

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody RegisterRequest request) {
		try{
			service.register(request);
			return ResponseEntity.ok(HttpStatus.CREATED);
		}catch (Exception e){
			return ResponseEntity.internalServerError().body(e);
		}
	}

	@PostMapping("/register-admin")
	public ResponseEntity<UserDTO> registerAdmin(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(service.registerAdmin(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity authenticate(@RequestBody AuthenticationRequest request) {
		try {
			return ResponseEntity.ok(service.authenticate(request));
		}catch (Exception e){
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/findAll")
	public List<User> findAll() {
		 List<User> users = repository.findAll();
		 return users;
	}


}
