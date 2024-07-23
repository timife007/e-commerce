package com.timife.services.impl;

import com.timife.feign.AuthFeignClient;
import com.timife.feign.InventoryFeignClient;
import com.timife.model.dtos.*;
import com.timife.model.dtos.completed_order_dtos.PurchasedOrder;
import com.timife.model.dtos.completed_order_dtos.PurchasedOrderItemDto;
import com.timife.model.entities.Cart;
import com.timife.model.entities.orders.Order;
import com.timife.model.entities.OrderItem;
import com.timife.model.entities.OrderStatus;
import com.timife.model.entities.orders.OrderedItem;
import com.timife.model.responses.CheckoutResponse;
import com.timife.model.responses.OrderResponse;
import com.timife.model.responses.ProductSizeResponse;
import com.timife.repositories.OrderItemRepository;
import com.timife.repositories.CartRepository;
import com.timife.repositories.OrderRepository;
import com.timife.services.CartService;
import com.timife.services.OrderPublisherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private InventoryFeignClient inventoryFeignClient;

    @Autowired
//    @LoadBalanced
    private AuthFeignClient authFeignClient;


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderPublisherService orderPublisherService;


    @Override
    public Cart selectOrder(OrderItemDto orderItemDto) {

        ProductSizeResponse productSize = inventoryFeignClient.selectOrderByProductSize(orderItemDto).getBody();
        OrderItem orderItem = orderItemRepository.findByCartUserIdAndProductSizeId(orderItemDto.getUserId(), productSize.getId());
        Cart presentCart = cartRepository.findByUserId(orderItemDto.getUserId());
        ReserveOrderItemDto reserveOrderItemDto = ReserveOrderItemDto.builder().productSizeId(productSize.getId()).qty(1).build();
        reserveProduct(reserveOrderItemDto);
        if (presentCart != null) {
            Double currentTotal = presentCart.getSubTotal();
            //reserve inventory item
            if (orderItem != null) {
                orderItem.setQty(orderItem.getQty() + 1);
                orderItem.setTotalPrice(orderItem.getTotalPrice() + productSize.getPrice());
                orderItem.setCart(presentCart);
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

    void reserveProduct(ReserveOrderItemDto reserveOrderItemDto) {
        Boolean isReserved = inventoryFeignClient.reserveProduct(reserveOrderItemDto).getBody();
        if (isReserved != null && !isReserved) {
            throw new RuntimeException("product not reserved successfully");
        }
    }

    @Override
    public Cart findCartById(Long userId) {
        try {
            return cartRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Cart with specified user not found");
        }
    }

    @Transactional
    @Override
    public Cart updateOrder(UpdateOrderItemDto updateOrderItemDto) {
        try {
            OrderItem orderItem = orderItemRepository.findById(updateOrderItemDto.getOrderItemId()).orElseThrow();
            Integer initialQty = orderItem.getQty();
            //reserve order and update  with respect to the new qty either +ve or -ve.
            ReserveOrderItemDto newReservedOrderItem = ReserveOrderItemDto
                    .builder()
                    .productSizeId(orderItem.getProductSizeId())
                    .qty(updateOrderItemDto.getQty() - initialQty)
                    .build();
            reserveProduct(newReservedOrderItem);
            orderItem.setQty(updateOrderItemDto.getQty());
            orderItem.setTotalPrice(orderItem.getUnitPrice() * updateOrderItemDto.getQty());
            orderItemRepository.save(orderItem);
            Cart cart = orderItem.getCart();
            cart.setSubTotal(cart.getOrderItems().stream().mapToDouble(OrderItem::getTotalPrice).sum());
            return cartRepository.save(cart);
        } catch (Exception e) {
            //We can have a compensation for when there is an exception and the reservation has been made
            //to undo the reservation.
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


    @Override
    public CheckoutResponse checkout(Integer userId) {
        Cart cart = cartRepository.findByUserId(Long.valueOf(userId));
        if (cart == null) {
            throw new RuntimeException("Cart is empty");
        }
        List<DeliveryAddressDto> addressDtoList = authFeignClient.getUserAddresses(userId).getBody();
        assert addressDtoList != null;
        DeliveryAddressDto deliveryAddressDto = addressDtoList.stream().filter(DeliveryAddressDto::getIsDefault).findFirst().orElse(null);
        Double deliveryFee = setDeliveryFeeBasedOn(deliveryAddressDto);
        Double totalFee = deliveryFee + cart.getSubTotal();
        cart.setSumTotal(totalFee);
        cart.setDeliveryFee(deliveryFee);
        cartRepository.save(cart);
        return CheckoutResponse.builder()
                .userId(Long.valueOf(userId))
                .orderItems(cart.getOrderItems())
                .subTotal(cart.getSubTotal())
                .deliveryFee(deliveryFee)  //delivery fee depending the default address.
                .sumTotal(totalFee)
                .deliveryAddressDto(addressDtoList)
                .build();
    }


    @Override
    public String confirmOrder(Long userId) {
        try {
            Cart cart = cartRepository.findByUserId(userId);
            Order newOrder = Order.builder()
                    .orderStatus(OrderStatus.ORDER_IN_PROGRESS)
                    .userId(cart.getUserId())
                    .orderedItemList(cart.getOrderItems()
                            .stream().map((item) -> OrderedItem.builder()
                                    .id(item.getId())
                                    .totalPrice(item.getTotalPrice())
                                    .unitPrice(item.getUnitPrice())
                                    .qty(item.getQty())
                                    .sizeId(item.getSizeId())
                                    .productId(item.getProductId())
                                    .productSizeId(item.getProductSizeId())
                                    .build()
                            ).toList())
                    .deliveryFee(cart.getDeliveryFee())
                    .sumTotal(cart.getSumTotal())
                    .build();

            Order order = orderRepository.save(newOrder);
            OrderResponse orderResponse = OrderResponse
                    .builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .orderStatus(order.getOrderStatus())
                    .sumTotal(order.getSumTotal())
                    .deliveryFee(order.getDeliveryFee())
                    .build();
            //Send topic to kafka to signal order successfully placed.
            //fix delivery fee.
            orderPublisherService.publishOrder(orderResponse);
            //cart should be deleted after confirming order
            return "Order successfully placed";
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional
    public void updateInventory(OrderResponse orderResponse) {
        try {
            log.error("USER ID ERROR: {}", orderResponse.getUserId().toString());
            List<PurchasedOrderItemDto> purchasedOrderItemDtos = cartRepository
                    .findByUserId(orderResponse.getUserId())
                    .getOrderItems().stream()
                    .map((orderItem -> PurchasedOrderItemDto.builder()
                            .cartId(orderItem.getId())
                            .qty(orderItem.getQty())
                            .productId(orderItem.getProductId())
                            .sizeId(orderItem.getSizeId())
                            .productSizeId(orderItem.getProductSizeId())
                            .id(orderItem.getId()).build())).toList();
            PurchasedOrder purchasedOrder = PurchasedOrder
                    .builder()
                    .orderStatus(orderResponse.getOrderStatus())
                    .purchasedOrderItemDtoList(purchasedOrderItemDtos)
                    .build();
            log.error("Approved Order: {}", purchasedOrder);
            orderPublisherService.publishCompletedOrder(purchasedOrder);  //errors
            //After sending completed or failed order, delete cart and items to refresh
            cartRepository.deleteById(orderResponse.getUserId());
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<OrderItem> getOrderItems(Long userId) {
        return orderItemRepository.findAll().stream().filter((orderItem -> orderItem.getCart().getUserId().equals(userId))).toList();
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

    private Double setDeliveryFeeBasedOn(DeliveryAddressDto deliveryAddressDto) {
        //TODO: Add a new delivery address if needed to the specific user
        //use the last index address as the default for each user.
        double deliveryFee = 0;
        if (deliveryAddressDto != null) {
            if (deliveryAddressDto.getCountry().equals("USA")) {
                deliveryFee = 10.0;
            } else {
                deliveryFee = 30.0;
            }
        }
        return deliveryFee;
    }
}


