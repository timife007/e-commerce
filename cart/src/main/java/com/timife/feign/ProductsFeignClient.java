package com.timife.feign;

import com.timife.model.dtos.DeliveryAddressDto;
import com.timife.model.dtos.OrderItemDto;
import com.timife.model.responses.ProductSizeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("CATEGORIES")
public interface ProductsFeignClient {

    @PostMapping("product/productSize")
    public ResponseEntity<ProductSizeResponse> selectOrderByProductSize(@RequestBody OrderItemDto selectOrderDto);
}