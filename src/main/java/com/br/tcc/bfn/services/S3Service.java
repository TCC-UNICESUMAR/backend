package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.ProductImageUrlDto;
import com.br.tcc.bfn.models.Product;
import com.br.tcc.bfn.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {

    String saveImageToS3(MultipartFile file, String bucketName) throws IOException;
    String getImageS3(String key, String bucketName) throws IOException, Exception;
    void saveImageToUser(MultipartFile file) throws Exception;
}
