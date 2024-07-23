package com.timife.model.entities.orders;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.timife.model.entities.Cart;
import com.timife.model.entities.OrderStatus;
import jakarta.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderedItem> orderedItemList;
    private Long userId;
    private Double deliveryFee = 0.0;
    private Double sumTotal = 0.0;
    private OrderStatus orderStatus;
}
