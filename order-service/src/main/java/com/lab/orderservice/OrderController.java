package com.lab.orderservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private List<Map<String, Object>> orders = new ArrayList<>();
    private int idCounter = 1;

    @GetMapping
    public List<Map<String, Object>> getOrders() {
        return orders;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody Map<String, Object> order) {
        Map<String, Object> newOrder = new HashMap<>(order);
        newOrder.put("id", idCounter++);
        newOrder.put("status", "PENDING");
        orders.add(newOrder);
        return ResponseEntity.status(201).body(newOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable int id) {
        return orders.stream()
                .filter(o -> o.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
