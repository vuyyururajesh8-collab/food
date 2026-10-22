package com.example.foodordering.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class OrderLine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long menuItemId;
    private String itemName;
    private Integer quantity;
    private BigDecimal unitPrice;
    public OrderLine() {}
    public OrderLine(Long orderId, Long menuItemId, String itemName, Integer quantity, BigDecimal unitPrice) {
        this.orderId=orderId; this.menuItemId=menuItemId; this.itemName=itemName;
        this.quantity=quantity; this.unitPrice=unitPrice;
    }
    public Long getId(){return id;} public Long getOrderId(){return orderId;}
    public Long getMenuItemId(){return menuItemId;} public String getItemName(){return itemName;}
    public Integer getQuantity(){return quantity;} public BigDecimal getUnitPrice(){return unitPrice;}
}
