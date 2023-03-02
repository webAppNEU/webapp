package com.webapp.userwebapp.repository;

import com.webapp.userwebapp.model.Image;
import com.webapp.userwebapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Integer> {

boolean existsByImageId(Integer imageId);
boolean existsByproductId(Integer productId);

    List<Object> findAllByProductId(Integer productId);
}
