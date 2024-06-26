package com.timife.services.impl;

import com.timife.models.entities.User;
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
}
