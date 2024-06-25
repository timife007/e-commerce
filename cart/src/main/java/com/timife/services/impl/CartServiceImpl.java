package com.timife.services.impl;

import com.timife.feign.ProductsFeignClient;
import com.timife.model.dtos.OrderItemDto;
import com.timife.repositories.OrderRepository;
import com.timife.services.CartService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductsFeignClient productsFeignClient;

    @Override
    public String selectOrder(OrderItemDto orderItemDto) {
        ResponseEntity<?> productSize = productsFeignClient.selectOrderByProductSize(orderItemDto);
        log.debug(productSize.toString());
        return productSize.toString();
    }
}
