package com.webapp.userwebapp.controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;
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
//
//    String clientRegion = "US-EAST-1";
//    String roleARN = "arn:aws:iam::729498506666:role/my_role";
//    String roleSessionName = "my-role";
//    @Value("${aws.s3.bucket.name}")
//    private String bucketName;
//
//    AmazonS3 s3Client;
    @Bean
    public AmazonS3 s3client() {

       return AmazonS3ClientBuilder.defaultClient();
//
//        try {
//            // Creating the STS client is part of your trusted code. It has
//            // the security credentials you use to obtain temporary security credentials.
//            AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
//                    .withCredentials(new ProfileCredentialsProvider())
//                    .withRegion(clientRegion)
//                    .build();
//
//            // Obtain credentials for the IAM role. Note that you cannot assume the role of an AWS root account;
//            // Amazon S3 will deny access. You must use credentials for an IAM user or an IAM role.
//            AssumeRoleRequest roleRequest = new AssumeRoleRequest()
//                    .withRoleArn(roleARN)
//                    .withRoleSessionName(roleSessionName);
//            AssumeRoleResult roleResponse = stsClient.assumeRole(roleRequest);
//            Credentials sessionCredentials = roleResponse.getCredentials();
//
//            // Create a BasicSessionCredentials object that contains the credentials you just retrieved.
//            BasicSessionCredentials awsCredentials = new BasicSessionCredentials(
//                    sessionCredentials.getAccessKeyId(),
//                    sessionCredentials.getSecretAccessKey(),
//                    sessionCredentials.getSessionToken());
//
//            // Provide temporary security credentials so that the Amazon S3 client
//            // can send authenticated requests to Amazon S3. You create the client
//            // using the sessionCredentials object.
//            s3Client = AmazonS3ClientBuilder.standard()
//                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                    .withRegion(clientRegion)
//                    .build();
//
//            // Verify that assuming the role worked and the permissions are set correctly
//            // by getting a set of object keys from the bucket.
//
//        } catch (AmazonServiceException e) {
//            // The call was transmitted successfully, but Amazon S3 couldn't process
//            // it, so it returned an error response.
//            e.printStackTrace();
//        } catch (SdkClientException a) {
//            // Amazon S3 couldn't be contacted for a response, or the client
//            // couldn't parse the response from Amazon S3.
//            a.printStackTrace();
//        }

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
//        return s3Client;
    }
}