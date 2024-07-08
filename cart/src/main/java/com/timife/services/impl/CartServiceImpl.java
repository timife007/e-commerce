package com.timife.services.impl;

import com.timife.feign.AuthFeignClient;
import com.timife.feign.ProductsFeignClient;
import com.timife.model.dtos.DeliveryAddressDto;
import com.timife.model.dtos.OrderItemDto;
import com.timife.model.dtos.UpdateOrderItemDto;
import com.timife.model.entities.Cart;
import com.timife.model.entities.OrderItem;
import com.timife.model.responses.CheckoutResponse;
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
import java.util.List;

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

    @Autowired
    private AuthFeignClient authFeignClient;


    @Override
    public Cart selectOrder(OrderItemDto orderItemDto) {

        ProductSizeResponse productSize = productsFeignClient.selectOrderByProductSize(orderItemDto).getBody();
        OrderItem orderItem = orderItemRepository.findByCartUserIdAndProductSizeId(orderItemDto.getUserId(), productSize.getId());
        Cart presentCart = cartRepository.findByUserId(orderItemDto.getUserId());
        if (presentCart != null) {
            Double currentTotal = presentCart.getSubTotal();
            if (orderItem != null) {
                orderItem.setQty(orderItem.getQty() + 1);
                orderItem.setTotalPrice(orderItem.getTotalPrice() + productSize.getPrice());
                presentCart.setSubTotal(currentTotal + productSize.getPrice());
                presentCart.getOrderItems().add(orderItem);
                return cartRepository.save(presentCart);
            }
            OrderItem newOrderItem = getOrderItem(productSize, orderItemDto, presentCart);
            presentCart.getOrderItems().add(newOrderItem);
            presentCart.setSubTotal(currentTotal + newOrderItem.getTotalPrice());
            return cartRepository.save(presentCart);
        }
        Cart newCart = new Cart();
        newCart.setUserId(orderItemDto.getUserId());
        OrderItem newOrderItem = getOrderItem(productSize, orderItemDto, newCart);
        newCart.getOrderItems().add(newOrderItem);
        newCart.setSubTotal(0.0 + newOrderItem.getTotalPrice());
        return cartRepository.save(newCart);
    }

    @Override
    public Cart findCartById(Long userId) {
        try {
            return cartRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Cart with specified user not found");
        }
    }

    @Override
    public Cart updateOrder(UpdateOrderItemDto updateOrderItemDto) {
        OrderItem orderItem = orderItemRepository.findById(updateOrderItemDto.getOrderItemId()).orElseThrow();
        orderItem.setQty(updateOrderItemDto.getQty());
        orderItem.setTotalPrice(orderItem.getUnitPrice() * updateOrderItemDto.getQty());
        orderItemRepository.save(orderItem);
        Cart cart = orderItem.getCart();
        cart.setSubTotal(cart.getOrderItems().stream().mapToDouble(OrderItem::getTotalPrice).sum());
        return cartRepository.save(cart);
    }

    @Override
    public String setDeliveryFeeBasedOn(String address) {
        //TODO: Add a new delivery address if needed to the specific user
        //use the last index address as the default for each user.
        return null;
    }

    @Override
    public CheckoutResponse checkout(Integer userId) {
        Cart cart = cartRepository.findByUserId(Long.valueOf(userId));
        List<DeliveryAddressDto> addressDtoList = authFeignClient.getUserAddresses(userId).getBody();
        Double deliveryFee = 30.0;
        Double totalFee = deliveryFee + cart.getSubTotal();
        return CheckoutResponse.builder()
                .userId(Long.valueOf(userId))
                .orderItems(cart.getOrderItems())
                .subTotal(cart.getSubTotal())
                .deliveryFee(20.0)
                .sumTotal(totalFee)
                .deliveryAddressDto(addressDtoList)
                .build();
    }

    public OrderItem getOrderItem(ProductSizeResponse productSize, OrderItemDto orderItemDto, Cart cart) {
        return OrderItem.builder()
                .productId(orderItemDto.getProductId())
                .qty(1)
                .productSizeId(productSize.getId())
                .sizeId(orderItemDto.getSizeId())
                .totalPrice(productSize.getPrice())
                .unitPrice(productSize.getPrice())
                .cart(cart)
                .build();
    }
}


