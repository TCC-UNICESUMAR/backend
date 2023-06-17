package com.br.tcc.bfn.services.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.models.Product;
import com.br.tcc.bfn.models.ProductImageKey;
import com.br.tcc.bfn.models.ProductImageUrl;
import com.br.tcc.bfn.models.User;
import com.br.tcc.bfn.repositories.ProductImageKeyRepository;
import com.br.tcc.bfn.repositories.ProductImageUrlRepository;
import com.br.tcc.bfn.repositories.ProductRepository;
import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.services.S3Service;
import com.br.tcc.bfn.utils.BfnConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class S3ServiceImpl implements S3Service {
    private final AmazonS3 amazonS3;
    @Value("${aws.s3.buckets.user}")
    private String bucketImageProfile;
    @Value("${aws.s3.buckets.product}")
    private String bucketImageProduct;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductImageKeyRepository productImageKeyRepository;
    private final ProductImageUrlRepository productImageUrlRepository;

    public S3ServiceImpl(AmazonS3 amazonS3, UserRepository userRepository, ProductRepository productRepository, ProductImageKeyRepository productImageKeyRepository, ProductImageUrlRepository productImageUrlRepository) {
        this.amazonS3 = amazonS3;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productImageKeyRepository = productImageKeyRepository;
        this.productImageUrlRepository = productImageUrlRepository;
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
            final Product product = productRepository.findByProductId(id);
            final String productImageId = UUID.randomUUID().toString();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 10);//validity of 10 minutes
            final String key = productImageId;
            final String url = amazonS3.generatePresignedUrl(bucketImageProduct, key, calendar.getTime(), HttpMethod.PUT).toString();
            List<ProductImageKey> listkeys = product.getProductImageKey() == null ? new ArrayList<>() : product.getProductImageKey();
            ProductImageKey productImageKey = new ProductImageKey();
            productImageKey.setProduct(product);
            productImageKey.setImageKey(key);
            productImageKey.setCreatedImageKeyAt(new Date());
            productImageKey.setUpdateImageKeyAt(new Date());
            productImageKeyRepository.save(productImageKey);
            listkeys.add(productImageKey);
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
                    new GeneratePresignedUrlRequest(bucketImageProfile, user.getProfileImageId())
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
            for (Product prod : products) {
                for (ProductImageKey imgKey : prod.getProductImageKey()) {
                    final S3Object object = amazonS3.getObject(bucketImageProduct, imgKey.getImageKey());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.HOUR, 1000);//validity of 10 minutes
                    GeneratePresignedUrlRequest generatePresignedUrlRequest =
                            new GeneratePresignedUrlRequest(bucketImageProduct, object.getKey())
                                    .withMethod(HttpMethod.GET)
                                    .withExpiration(calendar.getTime());
                    URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
                    ProductImageUrl productImageUrl = new ProductImageUrl();
                    productImageUrl.setImageUrl(url.toString());
                    productImageUrl.setProduct(prod);
                    productImageUrl.setCreatedImageUrlAt(new Date());
                    productImageUrl.setUpdateImageUrlAt(new Date());
                    productImageUrlRepository.save(productImageUrl);
                    prod.getProductImageUrls().add(productImageUrl);
                }

                productRepository.saveAll(products);

            }

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
            for (ProductImageKey key : product.getProductImageKey()) {
                final S3Object object = amazonS3.getObject(bucketImageProduct, key.getImageKey());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.HOUR, 10);//validity of 10 minutes
                GeneratePresignedUrlRequest generatePresignedUrlRequest =
                        new GeneratePresignedUrlRequest(bucketImageProduct, object.getKey())
                                .withMethod(HttpMethod.GET)
                                .withExpiration(calendar.getTime());
                URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
                urlImgs.add(url.toString());
            }

            return urlImgs;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void uploadCustomerProfileImage(MultipartFile[] files, Long customerId) throws IOException {

        for (MultipartFile multipartFile : files) {
            String key = UUID.randomUUID().toString();
            amazonS3.putObject(bucketImageProduct, key, multipartFile.getBytes().toString());
        }

    }
}
