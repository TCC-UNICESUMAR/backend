package com.br.tcc.bfn.services.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.models.Product;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.ProductRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.S3Service;
import com.br.tcc.bfn.utils.BfnConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class S3ServiceImpl implements S3Service {

    @Autowired
    private AmazonS3 amazonS3;
    @Autowired
    private Environment environment;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public String generateUrlPreSignedToProfileImage(final Long id) throws Exception {
        try {
            final User user = userRepository.findById(id).orElseThrow(() -> new UserException(BfnConstants.USER_NOT_FOUND));
            final String profileImageId = UUID.randomUUID().toString();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 10);
            final String key = "profile-image/%s/%s".formatted(id, profileImageId);//validity of 10 minutes
            final String url = amazonS3.generatePresignedUrl(environment.getProperty("aws.s3.buckets.user"), key, calendar.getTime(), HttpMethod.PUT).toString();
            user.setProfileImageId(key);
            userRepository.save(user);
            return url;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String generateUrlPreSignedToProductImage(final Long id) throws Exception {
        try {
            final Product product = productRepository.findByProductId(id);
            final String productImageId = UUID.randomUUID().toString();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 10);//validity of 10 minutes
            final String key = productImageId;
            final String url = amazonS3.generatePresignedUrl(environment.getProperty("aws.s3.buckets.product"), key, calendar.getTime(), HttpMethod.PUT).toString();
            productRepository.save(product);
            return url;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getObjectOnBucktUser(Long id) throws Exception {
        try {
            final User user = userRepository.findById(id).orElseThrow(() -> new UserException(BfnConstants.USER_NOT_FOUND));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, 10);//
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(environment.getProperty("aws.s3.buckets.user"), user.getProfileImageId())
                            .withMethod(HttpMethod.GET)
                            .withExpiration(calendar.getTime());
            URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Product> getImgOnBucketProductImage() throws Exception {
        try {
            final List<Product> products = productRepository.findAll();

            return products;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<String> getObjectOnBucketProductImage(Long id) throws Exception {
        try {
            List<String> urlImgs = new ArrayList<>();
            final Product product = productRepository.findByProductId(id);

            return urlImgs;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void uploadCustomerProfileImage(MultipartFile[] files, Long customerId) throws IOException {

        for (MultipartFile multipartFile : files) {
            String key = UUID.randomUUID().toString();
            amazonS3.putObject(environment.getProperty("aws.s3.buckets.product"), key, multipartFile.getBytes().toString());
        }

    }
}
