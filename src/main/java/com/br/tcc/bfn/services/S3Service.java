package com.br.tcc.bfn.services;

import com.br.tcc.bfn.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {

    String generateUrlPreSignedToProfileImage(final Long id) throws Exception;

    String generateUrlPreSignedToProductImage(final Long id) throws Exception;

    String getObjectOnBucktUser(Long id) throws Exception;

    List<String> getObjectOnBucketProductImage(Long id) throws Exception;

    void uploadCustomerProfileImage(MultipartFile[] files, Long customerId) throws IOException;
    List<Product> getImgOnBucketProductImage() throws Exception;
}
