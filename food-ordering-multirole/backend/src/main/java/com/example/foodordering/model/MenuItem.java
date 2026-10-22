package com.example.foodordering.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long hotelId;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private String imageUrl;
    private Boolean available = true;
    public MenuItem() {}
    public MenuItem(Long hotelId, String name, String description, String category, BigDecimal price, String imageUrl) {
        this.hotelId=hotelId; this.name=name; this.description=description; this.category=category;
        this.price=price; this.imageUrl=imageUrl; this.available=true;
    }
    public Long getId(){return id;} public Long getHotelId(){return hotelId;}
    public String getName(){return name;} public String getDescription(){return description;}
    public String getCategory(){return category;} public BigDecimal getPrice(){return price;}
    public String getImageUrl(){return imageUrl;} public Boolean getAvailable(){return available;}
}
