package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dto.RegisterRequest;
import com.br.tcc.bfn.dto.Response;
import com.br.tcc.bfn.dto.UserDTO;
import com.br.tcc.bfn.model.User;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.impl.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController{

    private final UserServiceImpl userServiceImpl;

    private final UserRepository repository;

    public UserController(UserServiceImpl userServiceImpl, UserRepository repository) {
        this.userServiceImpl = userServiceImpl;
        this.repository = repository;
    }

    @PostMapping("/register")
    public ResponseEntity<Response<UserDTO>> register(@RequestBody RegisterRequest request) {
        Response<UserDTO> dtoResponse = new Response<>();

        try{
            UserDTO userDTO = userServiceImpl.register(request);
            dtoResponse.setData(userDTO);
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
            UserDTO userDTO = userServiceImpl.registerAdmin(request);
            dtoResponse.setData(userDTO);
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
            userServiceImpl.disableUser(id);
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
            User user = repository.findById(id).get();
            if(user == null){
                throw new Exception("USER_NOT_FOUND");
            }
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(user);
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<UserDTO>> disableUser(@PathVariable Long id, @RequestBody RegisterRequest request){
        Response<UserDTO> dtoResponse = new Response<>();
        try{
            UserDTO userDTO = userServiceImpl.update(id,request);
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(userDTO);
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

}
