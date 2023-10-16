package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.AuthenticationRequest;
import com.br.tcc.bfn.dtos.AuthenticationResponse;
import com.br.tcc.bfn.dtos.Response;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService service;
	@Autowired
	private UserRepository repository;
	private final static Logger LOGGER = Logger.getLogger(AuthenticationController.class.getName());


	@Operation(summary = "Authenticate User on Application")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Authenticate User on Application",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = AuthenticationRequest.class)) }),
			@ApiResponse(responseCode = "500", description = "Error Authenticate User on Application",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Response.class)) })})
	@PostMapping("/authenticate")
	public ResponseEntity authenticate(@RequestBody AuthenticationRequest request, HttpServletRequest httpServletRequest) {
		try {
			return ResponseEntity.ok(service.authenticate(request, httpServletRequest));
		}catch (Exception e){
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Get Refresh Token on Application")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Get Refresh Token on Application",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = AuthenticationRequest.class)) }),
			@ApiResponse(responseCode = "500", description = "Error Get Refresh Token on Application",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Response.class)) })})
	@GetMapping("/refreshToken")
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
