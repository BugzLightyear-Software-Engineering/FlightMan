package com.flightman.flightmanapi.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.flightman.flightmanapi.model.User;
import com.flightman.flightmanapi.repositories.UserRepository;
import com.flightman.flightmanapi.services.UserService;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserService.class, UserRepository.class})
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserService userService;
    
    User user = new User("FN", "LN", "123456789", "r@domain.com",
                "passportNumber", "Address", "diet", 0);
    @Test
    public void whenSaveUser_shouldReturnUser() {
        
        
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
        Boolean created = userService.saveUser(user);
        assert(created == true);
        verify(userRepository).save(user);
        }

    @Test
    public void shouldReturnAllUsers(){
        List<User> users = new ArrayList<User>();
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);
        List<User> expected = userService.getAllUsers();
        assertEquals(expected, users);
        verify(userRepository).findAll();

    }

    @Test
    public void whenGivenId_shouldDeleteUser_ifFound(){
        when(userRepository.findByUserId(user.getID())).thenReturn(user);
        userService.deleteUserById(user.getID());
        verify(userRepository).deleteById(user.getID());
    }

}