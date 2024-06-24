package com.timife.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_order")
public class Order {
    private Long id;
    private Long userId;
    private Double subTotal;
    private Double sumTotal;
    List<OrderItem> orderItems;
}
