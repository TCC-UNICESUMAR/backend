package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.Response;
import com.br.tcc.bfn.dtos.UpdateAddressRequest;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.IUserService;
import com.br.tcc.bfn.services.impl.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/user")
public class UserController{

    private final IUserService userService;

    private final UserRepository repository;

    private final static Logger LOGGER = Logger.getLogger(TicketController.class.getName());

    public UserController(IUserService userService, UserRepository repository) {
        this.userService = userService;
        this.repository = repository;
    }

    @PostMapping("/register")
    public ResponseEntity<Response<UserDTO>> register(@RequestBody RegisterRequest request) {
        Response<UserDTO> dtoResponse = new Response<>();

        try{
            LOGGER.info("Method Register User Default");
            dtoResponse.setData(userService.register(request));
            dtoResponse.setStatusCode(HttpStatus.CREATED.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }
    }

    @PostMapping("/register-admin")
    public ResponseEntity<Response<UserDTO>> registerAdmin(@RequestBody RegisterRequest request){
        Response<UserDTO> dtoResponse = new Response<>();
        try{
            dtoResponse.setData(userService.registerAdmin(request));
            dtoResponse.setStatusCode(HttpStatus.CREATED.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<UserDTO>> disableUser(@PathVariable Long id){
        Response<UserDTO> dtoResponse = new Response<>();
        try{
            userService.disableUser(id);
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }

    }

    @GetMapping("/findAll")
    public Page<User> findAll(Pageable pageable) {
        Page<User> users = repository.findAll(pageable);
        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<User>> findById(@PathVariable Long id) {
        Response<User> dtoResponse = new Response<>();
        try {
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(repository.findById(id).get());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<UserDTO>> updateUser(@PathVariable Long id, @RequestBody RegisterRequest request){
        Response<UserDTO> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(userService.update(id,request));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<Response<UserDTO>> updateAddressUser(@PathVariable Long id,@Validated @RequestBody UpdateAddressRequest request){
        Response<UserDTO> dtoResponse = new Response<>();
        try{
            LOGGER.info("Method Register Address To User");
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(userService.updateAddress(id,request));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

}
