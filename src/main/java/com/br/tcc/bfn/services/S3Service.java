package com.br.tcc.bfn.services;

public interface S3Service {

    String generateUrlPreSignedToProfileImage(final Long id) throws Exception;

    String generateUrlPreSignedToProductImage(final Long id) throws Exception;

    void getObjectOnBucktUser(Long id) throws Exception;
}
