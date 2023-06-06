package com.br.tcc.bfn.services.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.br.tcc.bfn.services.S3Service;
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

    public S3ServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public String generateUrlPreSignedToProfileImage(final Long id) throws Exception {
        try {
            final String profileImageId = UUID.randomUUID().toString();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 10); //validity of 10 minutes
            return amazonS3.generatePresignedUrl(bucketImageProfile, "profile-image/%s/%s".formatted(id, profileImageId), calendar.getTime(), HttpMethod.PUT).toString();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String generateUrlPreSignedToProductImage(final Long id) throws Exception {
        try {
            final String productImageId = UUID.randomUUID().toString();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 10); //validity of 10 minutes
            return amazonS3.generatePresignedUrl(bucketImageProfile, "product-image/%s/%s".formatted(id, productImageId), calendar.getTime(), HttpMethod.PUT).toString();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
