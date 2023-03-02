package com.webapp.userwebapp.controller;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    public AmazonS3 s3Client1() {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new InstanceProfileCredentialsProvider(false))
                .build();
        return s3;
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