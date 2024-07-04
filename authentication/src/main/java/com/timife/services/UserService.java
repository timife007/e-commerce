package com.timife.services;

import com.timife.models.entities.DeliveryAddress;
import com.timife.models.entities.User;
import com.timife.models.requests.AddressRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//@RequiredArgsConstructor
public interface UserService {

    public Page<User> getAllUsers(Pageable pageable);
    public User getUser(String email);


    public boolean existsById(int id);

    public void clearUsers();

    public void deleteUser(int id);

    public DeliveryAddress addAddressToUser(AddressRequest address);
}
