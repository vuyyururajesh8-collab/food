package com.example.foodordering.controller;

import com.example.foodordering.dto.CreateOrderRequest;
import com.example.foodordering.model.CustomerOrder;
import com.example.foodordering.model.MenuItem;
import com.example.foodordering.model.OrderLine;
import com.example.foodordering.repository.CustomerOrderRepository;
import com.example.foodordering.repository.MenuItemRepository;
import com.example.foodordering.repository.OrderLineRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final CustomerOrderRepository orders;
    private final OrderLineRepository lines;
    private final MenuItemRepository menu;
    public OrderController(CustomerOrderRepository orders, OrderLineRepository lines, MenuItemRepository menu) {
        this.orders=orders; this.lines=lines; this.menu=menu;
    }

    @GetMapping
    public List<Map<String,Object>> getOrders() {
        return orders.findAll().stream().sorted(Comparator.comparing(CustomerOrder::getCreatedAt).reversed())
                .map(this::toMap).toList();
    }

    @GetMapping("/{id}")
    public Map<String,Object> getOrder(@PathVariable Long id) {
        CustomerOrder order = orders.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return toMap(order);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Map<String,Object> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        BigDecimal total = BigDecimal.ZERO;
        List<Map.Entry<MenuItem,Integer>> selected = new ArrayList<>();
        for (CreateOrderRequest.OrderItemRequest item : request.items()) {
            MenuItem menuItem = menu.findById(item.menuItemId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Menu item not found: " + item.menuItemId()));
            if (!Boolean.TRUE.equals(menuItem.getAvailable())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item is unavailable");
            selected.add(Map.entry(menuItem, item.quantity()));
            total = total.add(menuItem.getPrice().multiply(BigDecimal.valueOf(item.quantity())));
        }
        CustomerOrder order = orders.save(new CustomerOrder(request.customerName(), request.deliveryAddress(), "CONFIRMED_DEMO", total));
        for (var entry : selected) {
            MenuItem item = entry.getKey();
            lines.save(new OrderLine(order.getId(), item.getId(), item.getName(), entry.getValue(), item.getPrice()));
        }
        return toMap(order);
    }

    @PatchMapping("/{id}/status")
    public Map<String,Object> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Set<String> allowed = Set.of("CONFIRMED_DEMO", "PREPARING", "READY_FOR_PICKUP", "OUT_FOR_DELIVERY", "DELIVERED", "CANCELLED");
        String normalized = status.toUpperCase(Locale.ROOT);
        if (!allowed.contains(normalized)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported status");
        CustomerOrder order = orders.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        order.setStatus(normalized);
        return toMap(orders.save(order));
    }

    private Map<String,Object> toMap(CustomerOrder order) {
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("id", order.getId());
        result.put("customerName", order.getCustomerName());
        result.put("deliveryAddress", order.getDeliveryAddress());
        result.put("status", order.getStatus());
        result.put("total", order.getTotal());
        result.put("createdAt", order.getCreatedAt());
        result.put("items", lines.findByOrderId(order.getId()).stream().map(line -> Map.of(
                "menuItemId", line.getMenuItemId(), "name", line.getItemName(),
                "quantity", line.getQuantity(), "unitPrice", line.getUnitPrice())).toList());
        return result;
    }
}
