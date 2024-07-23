package com.timife.model.entities.orders;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordered_item")
public class OrderedItem {

    @Id
    private Long id;
    private Long productSizeId;
    private Long productId;
    private Long sizeId;
    private Integer qty;
    private Double totalPrice;
    private Double unitPrice;
}
