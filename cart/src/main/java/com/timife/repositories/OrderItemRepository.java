package com.timife.repositories;

import com.timife.model.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findByProductSizeId(Long productSizeId);

    OrderItem findByCartUserIdAndProductSizeId(Long cartUserId, Long productSizeId);
}
