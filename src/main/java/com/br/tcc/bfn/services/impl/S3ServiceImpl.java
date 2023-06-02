package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.s3.S3Buckets;
import com.br.tcc.bfn.services.IUserService;
import com.br.tcc.bfn.services.S3Service;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {

    private final S3Client s3;

    private final S3Buckets s3Buckets;

    private final IUserService userService;

    private final S3Presigner presigner;

    public S3ServiceImpl(S3Client s3, S3Buckets s3Buckets, IUserService userService, S3Presigner presigner) {
        this.s3 = s3;
        this.s3Buckets = s3Buckets;
        this.userService = userService;
        this.presigner = presigner;
    }

    @Override
    public String generateUrlPreSignedToProfileImage(final Long id) throws Exception {
        try {

            final String profileImageId = UUID.randomUUID().toString();

           final PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(s3Buckets.getCustomer())
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
