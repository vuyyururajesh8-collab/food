package com.example.foodordering.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="customers")
public class Customer {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String fullName;
 @Column(nullable=false,unique=true) private String email;
 @Column(nullable=false) private String phone;
 @Column(nullable=false) private String passwordHash;
 private Instant createdAt=Instant.now();
 public Customer(){}
 public Customer(String n,String e,String p,String h){fullName=n;email=e;phone=p;passwordHash=h;createdAt=Instant.now();}
 public Long getId(){return id;} public String getFullName(){return fullName;} public String getEmail(){return email;} public String getPhone(){return phone;}
}
