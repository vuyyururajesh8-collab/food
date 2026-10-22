package com.example.foodordering.controller;

import com.example.foodordering.model.Hotel;
import com.example.foodordering.model.MenuItem;
import com.example.foodordering.repository.HotelRepository;
import com.example.foodordering.repository.MenuItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HotelController {
    private final HotelRepository hotels;
    private final MenuItemRepository menu;
    public HotelController(HotelRepository hotels, MenuItemRepository menu) { this.hotels=hotels; this.menu=menu; }

    @GetMapping("/hotels")
    public List<Hotel> getHotels(@RequestParam(required=false) String q) {
        List<Hotel> all = hotels.findAll();
        if (q == null || q.isBlank()) return all;
        String term = q.trim().toLowerCase();
        return all.stream().filter(h -> h.getName().toLowerCase().contains(term)
                || h.getCuisine().toLowerCase().contains(term)
                || h.getDescription().toLowerCase().contains(term)).toList();
    }
    @GetMapping("/hotels/{id}")
    public Hotel getHotel(@PathVariable Long id) {
        return hotels.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));
    }
    @GetMapping("/hotels/{id}/menu")
    public List<MenuItem> getHotelMenu(@PathVariable Long id) {
        if (!hotels.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        return menu.findByHotelIdAndAvailableTrue(id);
    }
    @GetMapping("/menu/items")
    public List<MenuItem> getAllMenuItems() { return menu.findByAvailableTrue(); }
}
