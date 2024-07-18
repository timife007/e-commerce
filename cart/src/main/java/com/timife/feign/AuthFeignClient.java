package com.timife.feign;

import com.timife.model.dtos.DeliveryAddressDto;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "AUTHENTICATION")
public interface AuthFeignClient {
    @GetMapping("auth/deliveryAddress/{userId}")
    ResponseEntity<List<DeliveryAddressDto>> getUserAddresses(@PathVariable("userId") Integer userId);
}
