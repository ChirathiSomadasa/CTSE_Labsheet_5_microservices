package com.lab.itemservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
public class ItemController {

    private List<Map<String, Object>> items = new ArrayList<>();
    private int idCounter = 1;

    public ItemController() {
        items.add(Map.of("id", idCounter++, "name", "Book"));
        items.add(Map.of("id", idCounter++, "name", "Laptop"));
        items.add(Map.of("id", idCounter++, "name", "Phone"));
    }

    @GetMapping
    public List<Map<String, Object>> getItems() {
        return items;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addItem(@RequestBody Map<String, Object> item) {
        item.put("id", idCounter++);
        items.add(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable int id) {
        return items.stream()
                .filter(i -> i.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
