package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.*;
import com.br.tcc.bfn.exceptions.DonationException;
import com.br.tcc.bfn.facades.DonationFacade;
import com.br.tcc.bfn.services.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private IProductService productService;


    @Operation(summary = "Find All Category on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find All Category on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find All Category on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping()
    public ResponseEntity<Response<List<CategoryDto>>> getAllCategories(){
        Response<List<CategoryDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(productService.getAllCategories());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

}
