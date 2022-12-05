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

        public User getUserById(final UUID id) {
                return this.userRepository.findByUserId(id);
        }

        public User getUserByEmail(final String email) {
                return this.userRepository.findByEmail(email);
        }

        public Boolean deleteUserById(final UUID id) {
                return this.userRepository.deleteById(id) != null ? true : false;
        }

        public Boolean deleteUserByEmail(final String email) {
                return this.userRepository.deleteByEmail(email) != null ? true : false;
        }

        public Boolean saveUser(final User user) {
                return this.userRepository.save(user) != null;
        }
}
