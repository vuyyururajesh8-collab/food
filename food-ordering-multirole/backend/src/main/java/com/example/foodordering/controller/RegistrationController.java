package com.example.foodordering.controller;
import com.example.foodordering.model.*; import com.example.foodordering.repository.*;
import jakarta.validation.Valid; import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal; import java.util.*;
@RestController @RequestMapping("/api/v1")
public class RegistrationController {
 private final CustomerRepository customers; private final HotelRepository hotels;
 public RegistrationController(CustomerRepository c,HotelRepository h){customers=c;hotels=h;}
 public record CustomerRequest(@NotBlank @Size(max=100) String fullName,@NotBlank @Email String email,@NotBlank @Size(min=10,max=20) String phone,@NotBlank @Size(min=8,max=100) String password){}
 public record HotelRequest(@NotBlank @Size(max=140) String name,@NotBlank @Size(max=100) String cuisine,@NotBlank @Size(max=300) String address,@NotBlank @Email String ownerEmail,@NotBlank @Size(min=8,max=100) String password,@NotBlank String phone){}
 @PostMapping("/customers/register") @ResponseStatus(HttpStatus.CREATED)
 public Map<String,Object> customer(@Valid @RequestBody CustomerRequest r){
  String e=r.email().trim().toLowerCase(Locale.ROOT); if(customers.existsByEmailIgnoreCase(e))throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already registered");
  Customer c=customers.save(new Customer(r.fullName().trim(),e,r.phone().trim(),Integer.toHexString(r.password().hashCode())));
  return Map.of("id",c.getId(),"fullName",c.getFullName(),"email",c.getEmail(),"message","Customer registered");
 }
 @PostMapping("/hotels/register") @ResponseStatus(HttpStatus.CREATED)
 public Map<String,Object> hotel(@Valid @RequestBody HotelRequest r){
  Hotel h=hotels.save(new Hotel(r.name().trim(),r.cuisine().trim(),r.address().trim(),"https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=900",new BigDecimal("0.0"),35,new BigDecimal("25")));
  return Map.of("id",h.getId(),"name",h.getName(),"cuisine",h.getCuisine(),"message","Hotel registered and visible to customers");
 }
}
