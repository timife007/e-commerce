package com.timife.services.impl;

import com.timife.models.entities.DeliveryAddress;
import com.timife.models.entities.User;
import com.timife.models.requests.AddressRequest;
import com.timife.repositories.UserRepository;
import com.timife.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }


    @Override
    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    @Override
    public void clearUsers() {
        userRepository.deleteAll();
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public DeliveryAddress addAddressToUser(AddressRequest addressRequest) {
        User user = userRepository.findById(addressRequest.getUserId()).orElseThrow();
        DeliveryAddress newDeliveryAddress = DeliveryAddress.builder()
                .city(addressRequest.getCity())
                .country(addressRequest.getCountry())
                .phoneNumber(addressRequest.getMobile())
                .postCode(addressRequest.getZipCode())
                .firstName(addressRequest.getFirstName())
                .lastName(addressRequest.getLastName())
                .address(addressRequest.getAddress())
                .user(user)
                .postCode(addressRequest.getZipCode())
                .build();
        user.getDeliveryAddresses().add(newDeliveryAddress);
        return userRepository.save(user).getDeliveryAddresses().getLast();
    }


}
