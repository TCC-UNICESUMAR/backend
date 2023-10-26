package com.br.tcc.bfn.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {

    void saveImageToS3(MultipartFile[] files) throws IOException;
}
