package com.timife.services;

import com.timife.models.entities.User;
import com.timife.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

//@RequiredArgsConstructor
public interface UserService {

    public Page<User> getAllUsers(Pageable pageable);
    public User getUser(String email);


    public boolean existsById(int id);

    public void clearUsers();

    public void deleteUser(int id);
}
