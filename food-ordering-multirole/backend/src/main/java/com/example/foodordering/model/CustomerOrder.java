package com.example.foodordering.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "customer_orders")
public class CustomerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String deliveryAddress;
    private String status;
    private BigDecimal total;
    private Instant createdAt;

    public CustomerOrder() {}
    public CustomerOrder(String customerName, String deliveryAddress, String status, BigDecimal total) {
        this.customerName=customerName; this.deliveryAddress=deliveryAddress; this.status=status;
        this.total=total; this.createdAt=Instant.now();
    }
    public Long getId(){return id;} public String getCustomerName(){return customerName;}
    public String getDeliveryAddress(){return deliveryAddress;} public String getStatus(){return status;}
    public BigDecimal getTotal(){return total;} public Instant getCreatedAt(){return createdAt;}
    public void setStatus(String status){this.status=status;}
}
