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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadCustomerProfileImage(@RequestParam(defaultValue = "files") MultipartFile[] files) throws IOException {
        try {
            this.s3Service.saveImageToS3(files);
        }catch (IOException exc){
            throw new IOException();
        }
    }


}
