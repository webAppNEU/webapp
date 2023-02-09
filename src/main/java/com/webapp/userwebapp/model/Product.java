package com.webapp.userwebapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;


@Table(name="product")
@Entity
public class Product {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer productId;
    @NotNull
    @Column
    private String name;
    @Column
    @NotNull
    private String description;
    @NotNull
    @Column
    private String sku;
    @Column
    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    private Integer quantity;

    @Column
    @NotNull
    private LocalDateTime date_last_updated;

    @Column
    @NotNull
    private LocalDateTime date_added;
    @Column
    @NotNull
    private Integer owner_user_id;

    @Column
    @NotNull
    private String manufacturer;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDate_last_updated() {
        return date_last_updated;
    }

    public void setDate_last_updated(LocalDateTime date_last_updated) {
        this.date_last_updated = date_last_updated;
    }

    public LocalDateTime getDate_added() {
        return date_added;
    }

    public void setDate_added(LocalDateTime date_added) {
        this.date_added = date_added;
    }

    public Integer getOwner_user_id() {
        return owner_user_id;
    }

    public void setOwner_user_id(Integer owner_user_id) {
        this.owner_user_id = owner_user_id;
    }
}
