package com.timife.services;

import com.timife.models.entities.DeliveryAddress;
import com.timife.models.entities.User;
import com.timife.models.requests.AddressRequest;
import com.timife.models.responses.DeliveryAddressDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

//@RequiredArgsConstructor
public interface UserService {

    public Page<User> getAllUsers(Pageable pageable);
    public User getUser(String email);


    public boolean existsById(int id);

    public void clearUsers();

    public void deleteUser(int id);

    public DeliveryAddress addAddressToUser(AddressRequest address);


    public List<DeliveryAddressDto> getUserDeliveryAddresses(Integer userId);

    public DeliveryAddress updateDeliveryAddress(Long id, AddressRequest addressRequest);
}
