package com.timife.feign;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("AUTHENTICATION")
public interface AuthFeignClient {

    @GetMapping("auth/validate/{token}")
    public void validateToken(@PathVariable("token") String token);
}
