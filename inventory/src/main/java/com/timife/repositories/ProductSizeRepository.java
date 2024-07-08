package com.timife.repositories;

import com.timife.model.entities.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    Optional<ProductSize> findByProductIdAndSizeId(Long productId, Long sizeId);
}
