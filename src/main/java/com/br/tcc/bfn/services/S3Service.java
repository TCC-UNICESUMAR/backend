package com.br.tcc.bfn.services;

import software.amazon.awssdk.services.s3.presigner.S3Presigner;

public interface S3Service {

    String generateUrlPreSignedToProfileImage(Long id) throws Exception;
}
