package com.timife.services.impl;

import com.timife.models.entities.DeliveryAddress;
import com.timife.models.entities.User;
import com.timife.models.requests.AddressRequest;
import com.timife.models.responses.DeliveryAddressDto;
import com.timife.repositories.DeliveryAddressRepository;
import com.timife.repositories.UserRepository;
import com.timife.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final DeliveryAddressRepository deliveryAddressRepository;

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
                .state(addressRequest.getState())
                .phoneNumber(addressRequest.getMobile())
                .isBilling(addressRequest.getIsBilling())
                .isDefault(addressRequest.getIsDefault())
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

    @Override
    public List<DeliveryAddressDto> getUserDeliveryAddresses(Integer userId) {
        return userRepository.findById(userId).orElseThrow().getDeliveryAddresses().stream().map((deliveryAddress) ->
                DeliveryAddressDto
                        .builder()
                        .id(deliveryAddress.getId())
                        .userId(deliveryAddress.getUser().getId())
                        .firstName(deliveryAddress.getFirstName())
                        .lastName(deliveryAddress.getLastName())
                        .state(deliveryAddress.getState())
                        .city(deliveryAddress.getCity())
                        .postCode(deliveryAddress.getPostCode())
                        .isDefault(deliveryAddress.getIsDefault())
                        .isBilling(deliveryAddress.getIsBilling())
                        .phoneNumber(deliveryAddress.getPhoneNumber())
                        .country(deliveryAddress.getCountry())
                        .address(deliveryAddress.getAddress())
                        .build()
        ).toList();
    }

    @Override
    public DeliveryAddress updateDeliveryAddress(Long id, AddressRequest addressRequest) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(id).orElseThrow();
        deliveryAddress.setAddress(addressRequest.getAddress());
        deliveryAddress.setCity(addressRequest.getCity());
        deliveryAddress.setFirstName(addressRequest.getFirstName());
        deliveryAddress.setLastName(addressRequest.getLastName());
        deliveryAddress.setIsBilling(addressRequest.getIsBilling());
        deliveryAddress.setIsDefault(addressRequest.getIsDefault());
        return deliveryAddressRepository.save(deliveryAddress);
    }


}
