package com.webapp.userwebapp.controller;


import com.timgroup.statsd.StatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class S3BucketStorageController {

    @Autowired
    S3BucketStorageService service;

    @Autowired
    private StatsDClient statsDClient;


    @PostMapping("/v1/product/{productId}/image")
    public Object uploadFile(@RequestParam("fileName") String fileName,
                                             @RequestParam("file") MultipartFile file,@PathVariable int productId) {
        statsDClient.incrementCounter("uploadImage.service");
        return service.uploadFile(fileName,file,productId);
    }

    @DeleteMapping("/v1/product/{productId}/image/{imageid}")
    public  Object deleteFile(@PathVariable int imageid, @PathVariable int productId)
    {
        statsDClient.incrementCounter("DeleteImage.service");
        return service.deleteFile(productId,imageid);

    }
    @GetMapping("/v1/product/{productId}/image/{imageid}")
    public  Object detailsImage(@PathVariable int imageid, @PathVariable int productId)
    {
        statsDClient.incrementCounter("ViewImage.service");
        return service.detailsFile(productId,imageid);

    }


    @GetMapping("/v1/product/{productId}/image")
    public Object allImages(@PathVariable int productId)
    {

        statsDClient.incrementCounter("ViewAllImageDetails.service");
        return service.allImagesDetail(productId);
    }

}
