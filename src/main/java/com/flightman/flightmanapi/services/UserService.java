package com.flightman.flightmanapi.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightman.flightmanapi.model.User;
import com.flightman.flightmanapi.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return this.userRepository.findByUserId(id);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public Boolean deleteUserById(UUID id) {
        return this.userRepository.deleteById(id) != null ? true : false;
    }

    public Boolean deleteUserByEmail(String email) {
        return this.userRepository.deleteByEmail(email) != null ? true : false;
    }

    public Boolean saveUser(User user) {
        return this.userRepository.save(user) != null;
    }
}
