package com.flightman.flightmanapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightman.flightmanapi.model.User;
import com.flightman.flightmanapi.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
}
