package com.br.tcc.bfn.services.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.br.tcc.bfn.dtos.ProductImageUrlDto;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.models.Image;
import com.br.tcc.bfn.models.Product;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.ProductRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.s3.S3Config;
import com.br.tcc.bfn.services.IUserService;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class S3ServiceImpl implements S3Service {

    @Autowired
    private IUserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private S3Config amazon;
    @Autowired
    private Environment environment;

    private final static Logger LOGGER = LoggerFactory.getLogger(S3ServiceImpl.class);

    @Override
    public String saveImageToS3(MultipartFile file, String bucketName) throws IOException {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        amazon.getAmazonS3Client().putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return fileName;
    }

    @Override
    public void saveImageToUser(MultipartFile file) throws Exception {
        User user = userService.findAuth();
        String image = saveImageToS3(file, environment.getProperty("aws.s3.buckets.user"));
        user.setProfileImage(getImageS3(image, environment.getProperty("aws.s3.buckets.user")));
        userRepository.save(user);
    }

    @Override
    public String getImageS3(String key, String bucketName) throws Exception {
        LOGGER.info("Get Images from AWS...");
        try {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, 12);//
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, key)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(calendar.getTime());

            URL url = amazon.getAmazonS3Client().generatePresignedUrl(generatePresignedUrlRequest);

            LOGGER.info("Successful Get Images from AWS...");
            return url.toExternalForm();
        } catch (
                Exception io) {
            throw new Exception("ERROR ON GET IMAGES FROM AWS -> " + io.getMessage());
        }

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
