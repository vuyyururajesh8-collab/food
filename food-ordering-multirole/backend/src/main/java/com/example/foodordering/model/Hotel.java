package com.example.foodordering.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Hotel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cuisine;
    private String description;
    private String imageUrl;
    private BigDecimal rating;
    private Integer deliveryMinutes;
    private BigDecimal deliveryFee;

    public Hotel() {}
    public Hotel(String name, String cuisine, String description, String imageUrl,
                 BigDecimal rating, Integer deliveryMinutes, BigDecimal deliveryFee) {
        this.name=name; this.cuisine=cuisine; this.description=description; this.imageUrl=imageUrl;
        this.rating=rating; this.deliveryMinutes=deliveryMinutes; this.deliveryFee=deliveryFee;
    }
    public Long getId(){return id;} public String getName(){return name;}
    public String getCuisine(){return cuisine;} public String getDescription(){return description;}
    public String getImageUrl(){return imageUrl;} public BigDecimal getRating(){return rating;}
    public Integer getDeliveryMinutes(){return deliveryMinutes;} public BigDecimal getDeliveryFee(){return deliveryFee;}
}
