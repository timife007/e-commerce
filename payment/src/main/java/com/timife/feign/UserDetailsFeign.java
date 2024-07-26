package com.timife.feign;

import com.timife.model.responses.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "AUTHENTICATION")
public interface UserDetailsFeign {
    @GetMapping("auth/{userId}")
    ResponseEntity<UserResponse> getUserAddresses(@PathVariable("userId") Integer userId);
}
