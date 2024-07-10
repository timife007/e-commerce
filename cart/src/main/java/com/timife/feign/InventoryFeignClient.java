package com.timife.feign;

import com.timife.model.dtos.OrderItemDto;
import com.timife.model.dtos.ReserveOrderItemDto;
import com.timife.model.responses.ProductSizeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("INVENTORY")
public interface InventoryFeignClient {

    @PostMapping("product/productSize")
    public ResponseEntity<ProductSizeResponse> selectOrderByProductSize(@RequestBody OrderItemDto selectOrderDto);


    @PostMapping("product/reserve")
    public ResponseEntity<Boolean> reserveProduct(@RequestBody ReserveOrderItemDto reserveOrderItemDto);
}