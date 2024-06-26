package com.timife.services.impl;

import com.timife.feign.ProductsFeignClient;
import com.timife.model.dtos.OrderItemDto;
import com.timife.model.entities.Cart;
import com.timife.model.entities.OrderItem;
import com.timife.model.responses.ProductSizeResponse;
import com.timife.repositories.OrderItemRepository;
import com.timife.repositories.CartRepository;
import com.timife.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductsFeignClient productsFeignClient;

    @Override
    public OrderItem selectOrder(OrderItemDto orderItemDto) {

        ProductSizeResponse productSize = productsFeignClient.selectOrderByProductSize(orderItemDto).getBody();
        OrderItem orderItem = orderItemRepository.findByCartUserIdAndProductSizeId(orderItemDto.getUserId(), productSize.getId());
        Cart presentCart = cartRepository.findByUserId(orderItemDto.getUserId());
        if(presentCart != null){
            Double currentTotal = presentCart.getSumTotal();
            if (orderItem != null) {
                orderItem.setQty(orderItem.getQty() + orderItemDto.getQty());
                orderItem.setTotalPrice(orderItem.getTotalPrice() + productSize.getPrice());
//                presentCart.getOrderItems().add(orderItem);
                presentCart.setSumTotal(currentTotal + productSize.getPrice());
                cartRepository.save(presentCart);
                return orderItemRepository.save(orderItem);
            }
            OrderItem newOrderItem = OrderItem.builder()
                    .productId(orderItemDto.getProductId())
                    .qty(orderItemDto.getQty())
                    .productSizeId(productSize.getId())
                    .sizeId(orderItemDto.getSizeId())
                    .totalPrice(orderItemDto.getQty() * productSize.getPrice())
                    .cart(presentCart)
                    .build();
            presentCart.getOrderItems().add(newOrderItem);
            presentCart.setSumTotal(currentTotal + newOrderItem.getTotalPrice());
            cartRepository.save(presentCart);
            return orderItemRepository.save(newOrderItem);
        }
        Cart newCart = new Cart();
        newCart.setUserId(orderItemDto.getUserId());
        OrderItem newOrderItem = OrderItem.builder()
                .productId(orderItemDto.getProductId())
                .qty(orderItemDto.getQty())
                .productSizeId(productSize.getId())
                .sizeId(orderItemDto.getSizeId())
                .totalPrice(orderItemDto.getQty() * productSize.getPrice())
                .cart(newCart)
                .build();
        newCart.getOrderItems().add(newOrderItem);
        newCart.setSumTotal(0.0 + newOrderItem.getTotalPrice());
        cartRepository.save(newCart);
        return orderItemRepository.save(newOrderItem);
    }

    @Override
    public Cart findCartById(Long userId) {
        try {
            return cartRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Cart with specified user not found");
        }
    }
}
