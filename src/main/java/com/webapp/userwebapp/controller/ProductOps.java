package com.webapp.userwebapp.controller;

import com.timgroup.statsd.StatsDClient;
import com.webapp.userwebapp.UserWebAppApplication;
import com.webapp.userwebapp.model.Product;
import com.webapp.userwebapp.model.User;
import com.webapp.userwebapp.repository.ProductRepository;
import com.webapp.userwebapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class ProductOps
{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StatsDClient statsDClient;

    private static final Logger logger =  LoggerFactory.getLogger(UserWebAppApplication.class);

    public Object createProduct(Product product) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            Object object;
            if (user == null) {
                Object object1=HttpStatus.BAD_REQUEST;
                logger.info("Invalid username or username is null");
                object = new ResponseEntity(object1,HttpStatus.BAD_REQUEST);
            } else if (productRepository.existsBySku(product.getSku())) {
                Object object1=HttpStatus.BAD_REQUEST;
                logger.info( product.getSku() + " already exists");
                object = new ResponseEntity(object1,HttpStatus.BAD_REQUEST);

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
                statsDClient.incrementCounter("addProduct.service");
                logger.info(product.getName() +" added successfully");

            }
            return object;
        }
        catch (Exception e)
        {
            Object object = HttpStatus.BAD_REQUEST;
            logger.error(e +"");
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
                logger.info("Invalid product id");
                object = new ResponseEntity<>(HttpStatus.FORBIDDEN);
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
                statsDClient.incrementCounter("getProduct.service");
                logger.info("Product details for productId" + productId +" retrieved successfully");


            }
            return object;
        } catch (Exception e) {
            Object object = HttpStatus.BAD_REQUEST;
            logger.error(e +"");
            return new ResponseEntity<>(object,HttpStatus.BAD_REQUEST);
        }
}

    public Object PutProduct(Integer productId, Product productupdt) {
           try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = authentication.getName();
                String password = (String) authentication.getCredentials();
                User user = userRepository.findByUsername(username);
                Optional<Product> product = productRepository.findById(productId);
                Object object;
               if (product == null) {
                   object = HttpStatus.BAD_REQUEST;
                   logger.info("Invalid product Id");
                   return new ResponseEntity<>(object,HttpStatus.BAD_REQUEST);
               }else if (user.getUserId() != product.get().getOwner_user_id()) {
                    Object object1 = HttpStatus.FORBIDDEN;
                   logger.info("Cannot update product as it is owned by a different user");
                    object = new ResponseEntity(object1,HttpStatus.FORBIDDEN);
                }
               else if (productRepository.existsBySkuAndSkuNotLike(productupdt.getSku(), product.get().getSku())) {
                   Object object1 = HttpStatus.BAD_REQUEST;
                   logger.info("Invalid product Id");
                   object = new ResponseEntity(object1,HttpStatus.BAD_REQUEST);
               } else {
                    product.map(productupdate -> {
                        productupdate.setName(productupdt.getName());
                        productupdate.setManufacturer(productupdt.getManufacturer());
                        productupdate.setSku(productupdt.getSku());
                        productupdate.setDescription(productupdt.getDescription());
                        productupdate.setQuantity(productupdt.getQuantity());
                        productupdate.setDate_last_updated(LocalDateTime.now());
                        productRepository.save(productupdate);
                        logger.info(productupdt.getName() + " updated successfully");
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
                logger.error(e +"");
                return new ResponseEntity(object,HttpStatus.BAD_REQUEST);
            }
    }

    public Object updateProduct(Integer productId, Product productupdt) {

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            Optional<Product> product = productRepository.findById(productId);
            Object object;
            if (product == null) {
                Object object1 = HttpStatus.BAD_REQUEST;
                logger.info("Invalid product Id");
                object = new ResponseEntity<>(object1,HttpStatus.BAD_REQUEST);
            }
//            else if (productRepository.existsBySku(productupdt.getSku()) || productRepository.existsByDescription(productupdt.getDescription()) || productRepository.existsByName(productupdt.getName()) || productRepository.existsByQuantity(productupdt.getQuantity()) || productRepository.existsByManufacturer(productupdt.getManufacturer()) ) {
//                Object object1 = HttpStatus.FORBIDDEN;
//                object = new ResponseEntity(object1,HttpStatus.FORBIDDEN);
//            }
            else if (productRepository.existsBySkuAndSkuNotLike(productupdt.getSku(), product.get().getSku())) {
                Object object1 = HttpStatus.BAD_REQUEST;
                logger.info("Product SKU already exists");
                object = new ResponseEntity(object1,HttpStatus.BAD_REQUEST);
            }
            else if (user.getUserId() != product.get().getOwner_user_id()) {
                Object object1 = HttpStatus.FORBIDDEN;
                logger.info("Cannot update product "+ productId +" as it is owned by a different user");
                object = new ResponseEntity(object1,HttpStatus.FORBIDDEN);
            }
            else {
               // Product productput = new Product();
                product.map(productput -> {
                    if(productupdt.getName() == null)
                    {
                        productput.setName(product.get().getName());
                    }
                    else productput.setName(productupdt.getName());
                    if(productupdt.getManufacturer() == null)
                    {
                        productput.setManufacturer(product.get().getManufacturer());
                    }
                    else productput.setManufacturer(productupdt.getManufacturer());
                    if (productupdt.getSku() == null)
                    {
                        productput.setSku(product.get().getSku());
                    }
                    else productput.setSku(productupdt.getSku());
                    if (productupdt.getDescription() == null)
                    {
                        productput.setDescription(product.get().getDescription());
                    }
                    else productput.setDescription(productupdt.getDescription());
                    if (productupdt.getQuantity() == null)
                    {
                        productput.setQuantity(product.get().getQuantity());
                    }
                    else productput.setQuantity(productupdt.getQuantity());
//                product.map(productupdate -> {
//                    productupdate.setName(productupdt.getName());
//                    productupdate.setManufacturer(productupdt.getManufacturer());
//                    productupdate.setSku(productupdt.getSku());
//                    productupdate.setDescription(productupdt.getDescription());
//                    productupdate.setQuantity(productupdt.getQuantity());
//                    productupdate.setDate_last_updated(LocalDateTime.now());
//                    productRepository.save(productupdate);
//                    return null;
//                });
                productput.setDate_last_updated(LocalDateTime.now());
               // productRepository.save(productput);

//                    productupdate.setName(productupdt.getName());
//                    productupdate.setManufacturer(productupdt.getManufacturer());
//                    productupdate.setSku(productupdt.getSku());
//                    productupdate.setDescription(productupdt.getDescription());
//                    productupdate.setQuantity(productupdt.getQuantity());
//                    productupdate.setDate_last_updated(LocalDateTime.now());
                    productRepository.save(productput);
                    logger.info(productupdt.getName() + " has been updated");
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
            logger.info(e +"");
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
                logger.info("Invalid product Id");
                object = new ResponseEntity(object1,HttpStatus.NOT_FOUND);
            } else if (user.getUserId() != product.get().getOwner_user_id()) {
                Object object1 = HttpStatus.FORBIDDEN;
                logger.info("Cannot delete" + productId + " as it is owned by a different user");
                object = new ResponseEntity(object1,HttpStatus.FORBIDDEN);
            }
            
            else {
                productRepository.deleteById(productId);
                Object object1 = HttpStatus.NO_CONTENT;
                logger.info("Product " + productId + "  deleted successfully");
                object = new ResponseEntity(object1,HttpStatus.NO_CONTENT);

            }
            return object;
     }
        catch (NoSuchElementException e)
        {
            Object object = HttpStatus.NOT_FOUND;
            logger.info(e +"");
           return new ResponseEntity(object,HttpStatus.NOT_FOUND);
        }
        catch (Exception a)
        {
            Object object = HttpStatus.BAD_REQUEST;
            logger.info(a +"");
            return new ResponseEntity(object,HttpStatus.BAD_REQUEST);
        }
    }


}