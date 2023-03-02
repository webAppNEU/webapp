package com.webapp.userwebapp.controller;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class awscredentials {

//@Bean
//public AmazonS3 s3client(){
//
////    InstanceProfileCredentialsProvider instanceProfileCredentialsProvider = new InstanceProfileCredentialsProvider(false);
////    return  AmazonS3ClientBuilder.standard()
////            .withCredentials(instanceProfileCredentialsProvider)
////            .build();
//
//    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//            .build();
//    return s3Client;
//}

    @Bean
    public AmazonS3 s3Client() {
        AmazonS3 amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(Regions.DEFAULT_REGION)
                .build();
        return amazonS3;
    }

//    @Value("${aws.access.key.id}")
//    private String awsId;
//
//    @Value("${aws.secret.access.key}")
//    private String awsKey;
//
//    @Value("${aws.s3.region}")
//    private String region;
//
//    @Bean
//    public AmazonS3 s3client() {
//
//        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsId, awsKey);
//        AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
//                .withRegion(Regions.fromName(region))
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                .build();
//
//        return amazonS3Client;
//
//
//    }
}