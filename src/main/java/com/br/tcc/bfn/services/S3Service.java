package com.br.tcc.bfn.services;

public interface S3Service {

    String generateUrlPreSignedToProfileImage(Long id) throws Exception;
}
