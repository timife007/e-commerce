package com.timife.feign;

import com.timife.model.dtos.OrderItemDto;
import com.timife.model.responses.ProductSizeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("CATEGORIES")
public interface ProductsFeignClient {

    @PostMapping("product/productSize")
    public ResponseEntity<ProductSizeResponse> selectOrderByProductSize(@RequestBody OrderItemDto selectOrderDto);

}