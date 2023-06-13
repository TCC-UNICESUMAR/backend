package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.Response;
import com.br.tcc.bfn.models.Product;
import com.br.tcc.bfn.services.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/s3")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

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
    public ResponseEntity<Response<Void>> getObjectBucketUser(@PathVariable final Long id){
        Response<Void> dtoResponse = new Response<>();
        try{
            s3Service.getObjectOnBucktUser(id);
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }

    }
}
