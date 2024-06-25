package com.timife.controller;

import com.timife.model.dtos.OrderItemDto;
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

    @PostMapping("/productSize")
    public ResponseEntity<?> selectOrder( @RequestBody OrderItemDto orderItemDto) {
        try {
            return ResponseEntity.ok(cartService.selectOrder(orderItemDto));
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
