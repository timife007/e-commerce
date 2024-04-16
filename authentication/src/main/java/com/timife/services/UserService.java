package com.timife.services;

import com.timife.models.entities.User;
import com.timife.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

//@RequiredArgsConstructor
@Service
public interface UserService {

    public List<User> getAllUsers();
    public User getUser(String email);


    public boolean existsById(int id);

    public void clearUsers();

    public void deleteUser(int id);
}
