package com.br.tcc.bfn.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class S3Config {

    public static final String AWS_ACCESS_KEY = "aws.accessKey";
    public static final String AWS_SECRET_KEY = "aws.secretKey";
    public static final String AWS_REGION = "aws.region";
    @Autowired
    private Environment environment;

    @Bean
    public AmazonS3 getAmazonS3Client() {
        final BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials(environment.getProperty(AWS_ACCESS_KEY),
                environment.getProperty(AWS_SECRET_KEY));
        // Get Amazon S3 client and return the S3 client object
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAwsCredentials))
                .withRegion(environment.getProperty(AWS_REGION))
                .build();
    }
}