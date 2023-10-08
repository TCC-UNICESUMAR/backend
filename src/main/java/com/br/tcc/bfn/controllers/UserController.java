package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.AddressRequest;
import com.br.tcc.bfn.dtos.RegisterRequest;
import com.br.tcc.bfn.dtos.Response;
import com.br.tcc.bfn.dtos.UserDTO;
import com.br.tcc.bfn.facades.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/user")
public class UserController{

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private ModelMapper modelMapper;
    private final static Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Operation(summary = "Register new User on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Register new User Default",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Register User Default",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @PostMapping("/register")
    public ResponseEntity<Response<UserDTO>> register(@RequestBody RegisterRequest request) {
        Response<UserDTO> dtoResponse = new Response<>();

        try{
            LOGGER.info("Method Register User Default");
            dtoResponse.setData(userFacade.saveUser(request));
            dtoResponse.setStatusCode(HttpStatus.CREATED.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }
    }

    @Operation(summary = "Register Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register Admin",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Register Admin",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @PostMapping("/register-admin")
    public ResponseEntity<Response<UserDTO>> registerAdmin(@RequestBody RegisterRequest request){
        Response<UserDTO> dtoResponse = new Response<>();
        try{
            LOGGER.info("Method Register Admin");
            dtoResponse.setData(userFacade.saveUser(request));
            dtoResponse.setStatusCode(HttpStatus.CREATED.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }

    }

    @Operation(summary = "Disable User on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disable User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Disable User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class))})})
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<UserDTO>> disableUser(@PathVariable Long id){
        Response<UserDTO> dtoResponse = new Response<>();
        try{
            LOGGER.info("Method Disable User");
            userFacade.disableUser(id);
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }

    }

    @Operation(summary = "Find All Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find All Users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find All Users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class))})})
    @GetMapping("/findAll")
    public ResponseEntity<Response<Page<UserDTO>>> findAll(Pageable pageable) {
        LOGGER.info("Method Find All Users");
        Response<Page<UserDTO>> dtoResponse = new Response<>();
        dtoResponse.setStatusCode(HttpStatus.OK.value());
        dtoResponse.setData(userFacade.findAllWithPageable(pageable));
        return ResponseEntity.ok().body(dtoResponse);

    }

    @Operation(summary = "Find User By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find User By Id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<Response<UserDTO>> findById(@PathVariable Long id) {
        Response<UserDTO> dtoResponse = new Response<>();
        try {
            LOGGER.info("Method Find User By Id");
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(userFacade.findById(id));
            dtoResponse.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(UserController.class).updateUser(id, new RegisterRequest())).withSelfRel());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @GetMapping("/getUserBySession")
    public ResponseEntity<Response<UserDTO>> getUserSession() {
        Response<UserDTO> dtoResponse = new Response<>();
        try {
            LOGGER.info("Method Get User By Session");
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(userFacade.findAuth());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @Operation(summary = "Update User on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disable User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Disable User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<Response<UserDTO>> updateUser(@PathVariable Long id, @RequestBody RegisterRequest request){
        Response<UserDTO> dtoResponse = new Response<>();
        try{
            LOGGER.info("Method Update User");
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(userFacade.updateUser(id,request));
            dtoResponse.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(UserController.class).findById(id)).withSelfRel());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @Operation(summary = "Update User Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update User Address",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Update User Address",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class))})})
    @PutMapping("/address/{id}")
    public ResponseEntity<Response<UserDTO>> updateAddressUser(@PathVariable Long id,@Validated @RequestBody AddressRequest request){
        Response<UserDTO> dtoResponse = new Response<>();
        try{
            LOGGER.info("Method Update Address In User");
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(UserController.class).findById(id)).withSelfRel());
            dtoResponse.setData(userFacade.updateUserAddress(id,request));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @Operation(summary = "Save User Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Save User Address",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Save User Address",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class))})})
    @PostMapping("/address/{id}")
    public ResponseEntity<Response<UserDTO>> saveAddreesInUser(@PathVariable Long id,@Validated @RequestBody AddressRequest request){
        Response<UserDTO> dtoResponse = new Response<>();
        try{
            LOGGER.info("Method Register Address In User");
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(userFacade.saveUserAddress(id,request));
            dtoResponse.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(UserController.class).findById(id)).withSelfRel());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }



}
