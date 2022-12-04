package com.flightman.flightmanapi.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.flightman.flightmanapi.model.User;
import com.flightman.flightmanapi.repositories.UserRepository;
import com.flightman.flightmanapi.services.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { UserService.class, UserRepository.class })
public class UserServiceTest {
        @MockBean
        private UserRepository userRepository;

        @Autowired
        @InjectMocks
        private UserService userService;

        User user = new User("FN", "LN", "123456789", "r@domain.com",
                        "passportNumber", "Address", 1, 0);

        @Test
        public void whenSaveUser_shouldReturnUser() {

                Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
                Boolean created = userService.saveUser(user);
                assert (created == true);
                verify(userRepository).save(user);
        }

        @Test
        public void shouldReturnAllUsers() {
                List<User> users = new ArrayList<User>();
                users.add(new User());
                when(userRepository.findAll()).thenReturn(users);
                List<User> expected = userService.getAllUsers();
                assertEquals(expected, users);
                verify(userRepository).findAll();

        }

    @Test
    public void shouldDeleteUser_Id(){
        when(userRepository.deleteById(user.getUserId())).thenReturn(1);
        userService.deleteUserById(user.getUserId());
        verify(userRepository).deleteById(user.getUserId());
    }

    @Test
    public void shouldDeleteUser_Email(){
        when(userRepository.deleteByEmail(user.getEmail())).thenReturn(1);
        userService.deleteUserByEmail(user.getEmail());
        verify(userRepository).deleteByEmail(user.getEmail());
    }

    @Test
    public void shouldFindUser_Id(){
        when(userRepository.findByUserId(user.getUserId())).thenReturn(user);
        userService.getUserById(user.getUserId());
        verify(userRepository).findByUserId(user.getUserId());
    }

    @Test
    public void shouldFindUser_Email(){
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        userService.getUserByEmail(user.getEmail());
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    public void shouldSaveUser(){
        when(userRepository.save(user)).thenReturn(user);
        userService.saveUser(user);
        verify(userRepository).save(user);
    }
}