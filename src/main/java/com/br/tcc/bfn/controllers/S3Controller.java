package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.UploadImageProfileRequest;
import com.br.tcc.bfn.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/s3/uploadImageProfile")
public class S3Controller {
    @Autowired
    private S3Service s3Service;

    @PostMapping(consumes = { "multipart/form-data" })
    public void uploadCustomerProfileImage(@ModelAttribute UploadImageProfileRequest request) throws Exception {
        try {
            this.s3Service.saveImageToUser(Arrays.stream(request.getFile()).findFirst().get());
        }catch (Exception exc){
            throw new Exception(exc);
        }
    }


}
