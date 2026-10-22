package com.example.foodordering.controller;
import com.example.foodordering.dto.CreateMenuItemRequest; import com.example.foodordering.model.MenuItem;
import com.example.foodordering.repository.*; import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import java.util.List;
@RestController @RequestMapping("/api/v1/hotel-admin")
public class HotelAdminController {
 private final HotelRepository hotels; private final MenuItemRepository menu;
 public HotelAdminController(HotelRepository h,MenuItemRepository m){hotels=h;menu=m;}
 @GetMapping("/{id}/menu") public List<MenuItem> list(@PathVariable Long id){if(!hotels.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Hotel not found");return menu.findByHotelIdAndAvailableTrue(id);}
 @PostMapping("/{id}/menu") @ResponseStatus(HttpStatus.CREATED) public MenuItem add(@PathVariable Long id,@Valid @RequestBody CreateMenuItemRequest r){
 if(!hotels.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Hotel not found");
 String img=(r.imageUrl()==null||r.imageUrl().isBlank())?"https://images.unsplash.com/photo-1547592180-85f173990554?w=700":r.imageUrl().trim();
 return menu.save(new MenuItem(id,r.name().trim(),r.description().trim(),r.category().trim(),r.price(),img));}
}
