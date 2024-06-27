package com.timife.model.entities;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class CartTotalListener {
    @PrePersist
    @PreUpdate
    public void preUpdate(OrderItem orderItem) {
        Cart cart = orderItem.getCart();
        if (cart != null) {
            cart.updateTotalPrice();
        }
    }
}
