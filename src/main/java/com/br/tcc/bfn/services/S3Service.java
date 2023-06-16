package com.br.tcc.bfn.services;

import com.br.tcc.bfn.exceptions.ProductNotFoundException;

import java.io.IOException;
import java.util.List;

public interface S3Service {

    String generateUrlPreSignedToProfileImage(final Long id) throws Exception;

    String generateUrlPreSignedToProductImage(final Long id) throws Exception;

    String getObjectOnBucktUser(Long id) throws Exception;

    List<String> getObjectOnBucketProductImage(Long id) throws Exception;

}
