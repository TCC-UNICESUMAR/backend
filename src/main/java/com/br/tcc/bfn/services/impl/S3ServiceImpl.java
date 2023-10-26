package com.br.tcc.bfn.services.impl;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.br.tcc.bfn.repositories.ProductRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.s3.S3Config;
import com.br.tcc.bfn.services.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3ServiceImpl implements S3Service {

    @Autowired
    private Environment environment;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private S3Config amazon;

    private final static Logger LOGGER = LoggerFactory.getLogger(S3ServiceImpl.class);

    @Override
    public List<String> saveImageToS3(MultipartFile[] files, String bucketName) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for(MultipartFile file : files){
            File fileObj = convertMultiPartFileToFile(file);
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            fileNames.add(fileName);
            amazon.getAmazonS3Client().putObject(new PutObjectRequest(bucketName, fileName, fileObj));
            fileObj.delete();
        }

        return fileNames;
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            LOGGER.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
