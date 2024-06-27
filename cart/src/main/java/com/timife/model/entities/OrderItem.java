package com.timife.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_item")
@EntityListeners(CartTotalListener.class)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productSizeId;
    private Long productId;
    private Long sizeId;
    private Integer qty;
    private Double totalPrice;
    private Double unitPrice;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Cart cart;
}
