package com.br.tcc.bfn.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {

    List<String> saveImageToS3(MultipartFile[] files, String bucketName) throws IOException;
}
