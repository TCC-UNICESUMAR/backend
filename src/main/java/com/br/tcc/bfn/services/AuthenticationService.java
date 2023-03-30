package com.br.tcc.bfn.services;

import com.br.tcc.bfn.auth.AuthenticationRequest;
import com.br.tcc.bfn.auth.AuthenticationResponse;
import com.br.tcc.bfn.auth.RegisterRequest;
import com.br.tcc.bfn.config.JwtService;
import com.br.tcc.bfn.dto.UserDTO;
import com.br.tcc.bfn.model.Role;
import com.br.tcc.bfn.repositories.RoleRepository;
import com.br.tcc.bfn.model.User;
import com.br.tcc.bfn.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class AuthenticationService {
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final RoleRepository roleRepository;

	public AuthenticationService(UserRepository repository,
								 PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
		super();
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.roleRepository = roleRepository;
	}

	public void register(RegisterRequest request) {
		User user = new User();
		
		user.setFirstname(request.getFirstname());
		user.setFirstname(request.getFirstname());
		user.setLastname(request.getLastname());
		user.setEmail(request.getEmail());
		user.setCpfOrCnpj(request.getCnpjOrCpf());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		Role role = roleRepository.findById(1L).get();
		user.setRoles(Arrays.asList(role));
		repository.save(user);

	}

	public UserDTO registerAdmin(RegisterRequest request) {
		User user = new User();

		user.setFirstname(request.getFirstname());
		user.setFirstname(request.getFirstname());
		user.setLastname(request.getLastname());
		user.setEmail(request.getEmail());
		user.setRoles(roleRepository.findAll());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		repository.save(user);

		return new UserDTO(user.getEmail(), user.getAuthorities());
	}

	public Object authenticate(AuthenticationRequest request) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			User user = repository.findByEmail(request.getEmail()).get();
			if(user == null){
				throw new UsernameNotFoundException("User Not Found");
			}

			return new AuthenticationResponse(jwtService.generateToken(user));
		}catch (UsernameNotFoundException e){
			return e;
		}
	}

}
