package com.timife.repositories;

import com.timife.models.entities.DeliveryAddress;
import com.timife.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

    DeliveryAddress findByPostCodeAndUserId(String postCode, Long userId);
}
