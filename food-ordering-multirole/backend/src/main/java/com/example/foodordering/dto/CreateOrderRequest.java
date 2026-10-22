package com.example.foodordering.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record CreateOrderRequest(
        @NotBlank @Size(max=100) String customerName,
        @NotBlank @Size(max=300) String deliveryAddress,
        @NotEmpty List<@Valid OrderItemRequest> items
) {
    public record OrderItemRequest(@NotNull @Positive Long menuItemId, @NotNull @Min(1) @Max(50) Integer quantity) {}
}
