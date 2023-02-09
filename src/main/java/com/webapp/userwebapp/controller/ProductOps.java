package com.webapp.userwebapp.controller;

import com.webapp.userwebapp.model.Product;
import com.webapp.userwebapp.model.User;
import com.webapp.userwebapp.repository.ProductRepository;
import com.webapp.userwebapp.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class ProductOps
{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    public Object createProduct(Product product) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            Object object;
            if (user == null) {
                Object object1=HttpStatus.BAD_REQUEST;
                object = new ResponseEntity(object1,HttpStatus.BAD_REQUEST);
            } else if (productRepository.existsBySku(product.getSku())) {
                Object object1=HttpStatus.FORBIDDEN;
                object = new ResponseEntity(object1,HttpStatus.FORBIDDEN);

            } else {
                Product product1 = new Product();
                product1.setProductId(product.getProductId());
                product1.setName(product.getName());
                product1.setDescription(product.getDescription());
                product1.setSku(product.getSku());
                product1.setQuantity(product.getQuantity());
                product1.setDate_added(LocalDateTime.now());
                product1.setDate_last_updated(LocalDateTime.now());
                product1.setOwner_user_id(user.getUserId());
                product1.setManufacturer(product.getManufacturer());
                productRepository.save(product1);
                Object object1=HttpStatus.CREATED;
                object = new ResponseEntity(product1,HttpStatus.CREATED);


            }
            return object;
        }
        catch (Exception e)
        {
            Object object = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(object,HttpStatus.BAD_REQUEST);
        }
    }

    public Object selectProduct (Integer productId)
    {
        try {
            Map<String, String> productdetails = new HashMap<>();
            Optional<Product> product = productRepository.findById(productId);
            Object object;
            if (product == null) {
                object = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                productdetails.put("productId", String.valueOf(product.get().getProductId()));
                productdetails.put("name", product.get().getName());
                productdetails.put("description", product.get().getDescription());
                productdetails.put("sku", product.get().getSku());
                productdetails.put("manufacturer", product.get().getManufacturer());
                productdetails.put("quantity", String.valueOf(product.get().getQuantity()));
                productdetails.put("date_added", String.valueOf(product.get().getDate_added()));
                productdetails.put("date_last_updated", String.valueOf(product.get().getDate_last_updated()));
                productdetails.put("owner_user_id", String.valueOf(product.get().getOwner_user_id()));
                object = productdetails;


            }
            return object;
        } catch (Exception e) {
            Object object = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(object,HttpStatus.BAD_REQUEST);
        }
    }

    public Object updateProduct(Integer productId, Product productupdt) {

            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = authentication.getName();
                User user = userRepository.findByUsername(username);
                Optional<Product> product = productRepository.findById(productId);
                Object object;
                if (product == null) {
                    Object object1 = HttpStatus.BAD_REQUEST;
                    object = new ResponseEntity<>(object1,HttpStatus.BAD_REQUEST);
                } else if (productRepository.existsBySkuAndSkuNotLike(productupdt.getSku(), product.get().getSku())) {
                    Object object1 = HttpStatus.FORBIDDEN;
                    object = new ResponseEntity(object1,HttpStatus.FORBIDDEN);
                } else if (user.getUserId() != product.get().getOwner_user_id()) {
                    Object object1 = HttpStatus.UNAUTHORIZED;
                    object = new ResponseEntity(object1,HttpStatus.UNAUTHORIZED);
                } else {
                    product.map(productupdate -> {
                        productupdate.setName(productupdt.getName());
                        productupdate.setManufacturer(productupdt.getManufacturer());
                        productupdate.setSku(productupdt.getSku());
                        productupdate.setDescription(productupdt.getDescription());
                        productupdate.setQuantity(productupdt.getQuantity());
                        productupdate.setDate_last_updated(LocalDateTime.now());
                        productRepository.save(productupdate);
                        return null;
                    });
                    Object object1 = HttpStatus.NO_CONTENT;
                    object = new ResponseEntity(object1,HttpStatus.NO_CONTENT);
                }
                return object;
            }
            catch (Exception e)
            {
                Object object = HttpStatus.BAD_REQUEST;
                return new ResponseEntity(object,HttpStatus.BAD_REQUEST);
            }
    }

    public Object PutProduct(Integer productId, Product productupdt) {

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            Optional<Product> product = productRepository.findById(productId);
            Object object;
            if (product == null) {
                Object object1 = HttpStatus.BAD_REQUEST;
                object = new ResponseEntity<>(object1,HttpStatus.BAD_REQUEST);
            } else if (productRepository.existsBySku(productupdt.getSku()) || productRepository.existsByDescription(productupdt.getDescription()) || productRepository.existsByName(productupdt.getName()) || productRepository.existsByQuantity(productupdt.getQuantity()) || productRepository.existsByManufacturer(productupdt.getManufacturer()) ) {
                Object object1 = HttpStatus.FORBIDDEN;
                object = new ResponseEntity(object1,HttpStatus.FORBIDDEN);
            } else if (user.getUserId() != product.get().getOwner_user_id()) {
                Object object1 = HttpStatus.UNAUTHORIZED;
                object = new ResponseEntity(object1,HttpStatus.UNAUTHORIZED);
            } else {

                product.map(productupdate -> {
                    productupdate.setName(productupdt.getName());
                    productupdate.setManufacturer(productupdt.getManufacturer());
                    productupdate.setSku(productupdt.getSku());
                    productupdate.setDescription(productupdt.getDescription());
                    productupdate.setQuantity(productupdt.getQuantity());
                    productupdate.setDate_last_updated(LocalDateTime.now());
                    productRepository.save(productupdate);
                    return null;
                });
                Object object1 = HttpStatus.NO_CONTENT;
                object = new ResponseEntity(object1,HttpStatus.NO_CONTENT);
            }
            return object;
        }
        catch (Exception e)
        {
            Object object = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(object,HttpStatus.BAD_REQUEST);
        }
    }

    public Object DeleteProduct(Integer productId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            Optional<Product> product = productRepository.findById(productId);
            Object object;
            if (product == null) {
                Object object1 = HttpStatus.NOT_FOUND;
                object = new ResponseEntity(object1,HttpStatus.NOT_FOUND);
            } else if (user.getUserId() != product.get().getOwner_user_id()) {
                Object object1 = HttpStatus.FORBIDDEN;
                object = new ResponseEntity(object1,HttpStatus.FORBIDDEN);
            }
            
            else {
                productRepository.deleteById(productId);
                Object object1 = HttpStatus.NO_CONTENT;
                object = new ResponseEntity(object1,HttpStatus.NO_CONTENT);

            }
            return object;
        }
        catch (Exception e)
        {
            Object object = HttpStatus.BAD_REQUEST;
           return new ResponseEntity(object,HttpStatus.BAD_REQUEST);
        }
    }


}