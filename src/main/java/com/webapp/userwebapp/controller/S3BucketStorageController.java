package com.webapp.userwebapp.controller;


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


    @PostMapping("/v1/product/{productId}/image")
    public Object uploadFile(@RequestParam("fileName") String fileName,
                                             @RequestParam("file") MultipartFile file,@PathVariable int productId) {
        return service.uploadFile(fileName,file,productId);
    }

    @DeleteMapping("/v1/product/{productId}/image/{imageid}")
    public  Object deleteFile(@PathVariable int imageid, @PathVariable int productId)
    {
        return service.deleteFile(productId,imageid);

    }
    @GetMapping("/v1/product/{productId}/image/{imageid}")
    public  Object detailsImage(@PathVariable int imageid, @PathVariable int productId)
    {
        return service.detailsFile(productId,imageid);

    }


    @GetMapping("/v1/product/{productId}/image")
    public Object allImages(@PathVariable int productId)
    {
        return service.allImagesDetail(productId);
    }

}
