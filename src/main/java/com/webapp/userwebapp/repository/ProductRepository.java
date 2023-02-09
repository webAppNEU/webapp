package com.webapp.userwebapp.repository;


import com.webapp.userwebapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    boolean existsBySku(String SKU);

    boolean existsBySkuAndSkuNotLike(String SKU,String SKU1);

    boolean existsByDescription(String description);
    boolean existsByManufacturer(String manufacturer);
    boolean existsByQuantity (Integer quantity);
    boolean existsByName(String name);

}
