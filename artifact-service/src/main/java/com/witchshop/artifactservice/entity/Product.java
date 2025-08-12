package com.witchshop.artifactservice.entity;

import lombok.Data;

@Data
public class Product {
    private String quality;
    private Integer durability;
    private String ErrorMessage;
    private Boolean createdSuccessfully;

    public Product(String quality, Integer durability) {
        this.quality = quality;
        this.durability = durability;
        this.createdSuccessfully = true;
    }
    public Product(String errorMessage) {
        this.ErrorMessage = errorMessage;
        this.createdSuccessfully = false;
    }
}
