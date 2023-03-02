package com.webapp.userwebapp.controller;


import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.webapp.userwebapp.model.Image;
import com.webapp.userwebapp.model.Product;
import com.webapp.userwebapp.model.User;
import com.webapp.userwebapp.repository.ImageRepository;
import com.webapp.userwebapp.repository.ProductRepository;
import com.webapp.userwebapp.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@Service
public class S3BucketStorageService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ImageRepository imageRepository;


     AmazonS3 amazonS3Client = AmazonS3ClientBuilder.defaultClient();

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    /**
     * Upload file into AWS S3
     *
     * @param keyName
     * @param file
     * @return String
     */
    public Object uploadFile(String keyName, MultipartFile file, Integer productId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            Optional<Product> product = productRepository.findById(productId);
            Object object;
            if (product == null) {
                Object object1 = HttpStatus.FORBIDDEN;
                object = new ResponseEntity<>(object1, HttpStatus.FORBIDDEN);
            } else if (user.getUserId() != product.get().getOwner_user_id()) {
                Object object1 = HttpStatus.UNAUTHORIZED;
                object = new ResponseEntity(object1, HttpStatus.UNAUTHORIZED);
            } else {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                UUID uuid = UUID.randomUUID();

                String keynamepath = uuid + "/" + keyName;
                amazonS3Client.putObject(bucketName, keynamepath, file.getInputStream(), metadata);
                Image image = new Image();
                image.setFilename(keyName);
                image.setDate_created(LocalDateTime.now());
                image.setS3_bucket_path(keynamepath);
                image.setproductId(productId);
                imageRepository.save(image);
                object = image;

            }
            return object;
        } catch (AmazonServiceException serviceException) {
            Object object = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(object, HttpStatus.BAD_REQUEST);
        } catch (AmazonClientException clientException) {
            Object object = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(object, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            Object object = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(object, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Object object = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(object, HttpStatus.BAD_REQUEST);
        }
    }

    public Object deleteFile(Integer productId, Integer imageId) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            Optional<Product> product = productRepository.findById(productId);
            Object object;
            if (product == null) {
                Object object1 = HttpStatus.NOT_FOUND;
                object = new ResponseEntity<>(object1, HttpStatus.NOT_FOUND);
            } else if (user.getUserId() != product.get().getOwner_user_id()) {
                Object object1 = HttpStatus.UNAUTHORIZED;
                object = new ResponseEntity(object1, HttpStatus.UNAUTHORIZED);
            } else if (imageRepository.existsByImageId(imageId) & imageRepository.existsByproductId(productId)) {
                Optional<Image> image = imageRepository.findById(imageId);

                if (image.get().getproductId() == productId && image.get().getImageId() == imageId) {
                    String keynamepath = image.get().getS3_bucket_path();
                    amazonS3Client.deleteObject(bucketName, keynamepath);
                    imageRepository.deleteById(imageId);
                    object = new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    object = new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            } else {
                object = new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return object;
        } catch (Exception e) {
            Object object = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            return object;
        }
    }


    public Object detailsFile(Integer productId, Integer imageId) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            Optional<Product> product = productRepository.findById(productId);
            Object object;
            if (product == null) {
                Object object1 = HttpStatus.FORBIDDEN;
                object = new ResponseEntity<>(object1, HttpStatus.FORBIDDEN);

            } else if (user.getUserId() != product.get().getOwner_user_id()) {
                Object object1 = HttpStatus.UNAUTHORIZED;
                object = new ResponseEntity(object1, HttpStatus.UNAUTHORIZED);
            } else if (imageRepository.existsByImageId(imageId) & imageRepository.existsByproductId(productId)) {
                Optional<Image> image = imageRepository.findById(imageId);
                if (image.get().getproductId() == productId && image.get().getImageId() == imageId) {
                    Map<String, String> imagedetails = new HashMap<>();
                    imagedetails.put("ImageId", String.valueOf(image.get().getImageId()));
                    imagedetails.put("ProductId", String.valueOf(image.get().getImageId()));
                    imagedetails.put("filename", String.valueOf(image.get().getFilename()));
                    imagedetails.put("date_created", String.valueOf(image.get().getDate_created()));
                    imagedetails.put("s3_bucket_path", String.valueOf(image.get().getS3_bucket_path()));
                    object = imagedetails;
                } else {
                    object = new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }


            } else {
                object = new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            return object;

        } catch (Exception e) {
            Object object = new ResponseEntity<>(HttpStatus.FORBIDDEN);
            return object;
        }
    }


    public Object allImagesDetail(Integer productId) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            Optional<Product> product = productRepository.findById(productId);
            List<Object> object;
            if (product == null) {
                Object object1 = HttpStatus.FORBIDDEN;
                object = Collections.singletonList(new ResponseEntity<>(object1, HttpStatus.FORBIDDEN));

            } else if (user.getUserId() != product.get().getOwner_user_id()) {
                Object object1 = HttpStatus.UNAUTHORIZED;
                object = (List<Object>) new ResponseEntity(object1, HttpStatus.UNAUTHORIZED);
            } else {
                List<Object> objectList = imageRepository.findAllByProductId(productId);
                object = objectList;
            }
            return object;
        } catch (Exception e) {
//            Object object = new ResponseEntity<>(HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

}