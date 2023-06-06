package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.s3.S3Buckets;
import com.br.tcc.bfn.services.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {
    private final S3Buckets s3Buckets;
    @Value("${aws.region}")
    private String awsRegion;

    public S3ServiceImpl(S3Buckets s3Buckets) {
        this.s3Buckets = s3Buckets;
    }


    @Override
    public String generateUrlPreSignedToProfileImage(final Long id) throws Exception {
        try {

            final String profileImageId = UUID.randomUUID().toString();

            ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();

            S3Presigner presigner = S3Presigner.builder()
                    .region(Region.of(awsRegion))
                    .credentialsProvider(credentialsProvider)
                    .build();

            final PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(s3Buckets.getCustomer())
                    .contentType("image/png")
                    .key("profile-image/%s/%s".formatted(id, profileImageId))
                    .build();

            final PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(30))
                    .putObjectRequest(objectRequest)
                    .build();

            final PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);

            return presignedRequest.url().toString();

        } catch (S3Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
