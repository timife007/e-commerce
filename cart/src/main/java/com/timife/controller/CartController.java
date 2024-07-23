package com.timife.controller;

import com.timife.model.dtos.OrderItemDto;
import com.timife.model.dtos.UpdateOrderItemDto;
import com.timife.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/select")
    public ResponseEntity<?> selectOrder(@RequestBody OrderItemDto orderItemDto) {
        try {
            return ResponseEntity.ok(cartService.selectOrder(orderItemDto));
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @GetMapping("/checkout/{userId}")
    public ResponseEntity<?> checkOut(@PathVariable("userId") Integer userId) {
        try {
            return ResponseEntity.ok(cartService.checkout(userId));
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> viewCart(@PathVariable("id") Long userId) {
        try {
            return ResponseEntity.ok(cartService.findCartById(userId));
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateOrderItem(@RequestBody UpdateOrderItemDto updateOrderItemDto) {
        try {
            return ResponseEntity.ok(cartService.updateOrder(updateOrderItemDto));
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @PostMapping("confirmOrder/{userId}")
    public ResponseEntity<?> confirmOrder(@PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(cartService.confirmOrder(userId));
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @GetMapping("orderItems/{id}")
    public ResponseEntity<?> getAllOrders(@PathVariable("id") Long userId) {
        try {
            return ResponseEntity.ok(cartService.getOrderItems(userId));
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
