package com.example.foodordering.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal;
public record CreateMenuItemRequest(@NotBlank @Size(max=120) String name,@NotBlank @Size(max=300) String description,@NotBlank @Size(max=80) String category,@NotNull @DecimalMin("1.00") BigDecimal price,@Size(max=500) String imageUrl){}
