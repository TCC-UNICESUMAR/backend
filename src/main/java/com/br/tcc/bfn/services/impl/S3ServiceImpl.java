package com.br.tcc.bfn.services.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.br.tcc.bfn.exceptions.ProductNotFoundException;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.models.Product;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.ProductRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.S3Service;
import com.br.tcc.bfn.utils.BfnConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {
    private final AmazonS3 amazonS3;
    @Value("${aws.s3.buckets.user}")
    private String bucketImageProfile;
    @Value("${aws.s3.buckets.product}")
    private String bucketImageProduct;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    public S3ServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public String generateUrlPreSignedToProfileImage(final Long id) throws Exception {
        try {
            final User user = userRepository.findById(id).orElseThrow(() -> new UserException(BfnConstants.USER_NOT_FOUND));
            final String profileImageId = UUID.randomUUID().toString();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 10);
            final String key = "profile-image/%s/%s".formatted(id, profileImageId);//validity of 10 minutes
            final String url = amazonS3.generatePresignedUrl(bucketImageProfile, key, calendar.getTime(), HttpMethod.PUT).toString();
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
            final Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(BfnConstants.PRODUCT_NOT_FOUND));
            final String productImageId = UUID.randomUUID().toString();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 10);//validity of 10 minutes
            final String key = "product-image/%s/%s".formatted(id,productImageId);//validity of 10 minutes
            final String url = amazonS3.generatePresignedUrl(bucketImageProfile, key, calendar.getTime(), HttpMethod.PUT).toString();
            product.setImageProductKey(key);
            productRepository.save(product);
            return url;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void getObjectOnBucktUser(Long id) throws Exception {
        try {
            final User user = userRepository.findById(id).orElseThrow(() -> new UserException(BfnConstants.USER_NOT_FOUND));
            final S3Object object = amazonS3.getObject(bucketImageProfile, user.getProfileImageId());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
