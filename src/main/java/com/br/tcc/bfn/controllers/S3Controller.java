package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.Response;
import com.br.tcc.bfn.exceptions.ProductNotFoundException;
import com.br.tcc.bfn.services.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/s3")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @Operation(summary = "Get Pre Signed Url on AWS S3 To Upload User Profile Image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Pre Signed Url on AWS S3 To Upload User Profile Image",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Get Pre Signed Url on AWS S3 To Upload User Profile Image",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping("/preSignedUrlUser/{id}")
    public ResponseEntity<Response<String>> getUrlToProfileImage(@PathVariable final Long id){
        Response<String> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(s3Service.generateUrlPreSignedToProfileImage(id));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }

    }
    @Operation(summary = "Get Pre Signed Url on AWS S3 To Upload Product Image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Pre Signed Url on AWS S3 To Upload Product Image",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Get Pre Signed Url on AWS S3 To Upload Product Image",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping("/preSignedUrlProduct/{id}")
    public ResponseEntity<Response<String>> getUrlToProductImage(@PathVariable final Long id){
        Response<String> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(s3Service.generateUrlPreSignedToProductImage(id));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }

    }

    @GetMapping("/getObjectUserProfile/{id}")
    public ResponseEntity<String> getObjectBucketUser(@PathVariable final Long id) throws Exception {
        try {
            return ResponseEntity.ok().body(s3Service.getObjectOnBucktUser(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/getObjectProductImage/{id}")
    public ResponseEntity<List<String>> getObjectBucketProduct(@PathVariable final Long id) throws Exception {
        try {
            return ResponseEntity.ok().body(s3Service.getObjectOnBucketProductImage(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Collections.singletonList(e.getMessage()));
        }
    }

    @PostMapping(
            value = "{productId}/uploadImage",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void uploadCustomerProfileImage(
            @PathVariable("productId") Long productId,
            @RequestParam(defaultValue = "files") MultipartFile[] files) throws IOException {

        s3Service.uploadCustomerProfileImage(files,productId);

    }


}
