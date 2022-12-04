package com.flightman.flightmanapi.integration.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.util.ReflectionTestUtils;

import com.flightman.flightmanapi.controller.UserController;
import com.flightman.flightmanapi.model.User;
import com.flightman.flightmanapi.services.UserService;
import com.flightman.flightmanapi.repositories.UserRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.http.HttpHeaders;
import org.springframework.util.Base64Utils;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@ActiveProfiles
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    User user = new User("FN", "LN", "123456789", "r@domain.com",
                        "passportNumber", "Address", 1, 0);
    
    private String auth_user = "abhilash";
    private String password = "securedpasswordofsrishti";

    @Test
    public void getUser() throws Exception {
        List<User> created = new ArrayList<User>();
        created.add(user);
        given(userService.getAllUsers()).willReturn(created);
        mockMvc.perform(
                        get("/api/users")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.auth_user + ":" + this.password).getBytes()))
                        .accept(MediaType.ALL)).andExpect(status().isOk());
    }

    @Test
    public void getUserByEmail() throws Exception {
        when(userRepository.findByEmail(any())).thenReturn(user);
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        mockMvc.perform(
                        get("/api/user/email/{email}", user.getEmail())
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.auth_user + ":" + this.password).getBytes()))
                        .accept(MediaType.ALL));
    }

    @Test
    public void getUserById() throws Exception {
        when(userRepository.findByUserId(any())).thenReturn(user);
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        mockMvc.perform(
                        get("/api/user/id/{id}", "7a9223a4-820e-42d8-922b-162cea9e5f6e")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.auth_user + ":" + this.password).getBytes()))
                        .accept(MediaType.ALL));
    }

    @Test
    public void deleteByEmail() throws Exception {
        when(userRepository.deleteByEmail(any())).thenReturn(1);
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        mockMvc.perform(
                        delete("/api/user/email/{email}", user.getEmail())
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.auth_user + ":" + this.password).getBytes()))
                        .accept(MediaType.ALL)).andExpect(status().isOk());
    }

    @Test
    public void deleteById() throws Exception {
        when(userRepository.deleteById(any(UUID.class))).thenReturn(1);
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);

        mockMvc.perform(
                        delete("/api/user/id/{id}", "7a9223a4-820e-42d8-922b-162cea9e5f6e")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.auth_user + ":" + this.password).getBytes()))
                        .accept(MediaType.ALL)).andExpect(status().isOk());
    }

    @Test
    public void getUserByEmail_2() throws Exception {
        when(userService.getUserByEmail(any())).thenThrow(new RuntimeException());

        mockMvc.perform(
                        get("/api/user/email/{email}", user.getEmail())
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.auth_user + ":" + this.password).getBytes()))
                        .accept(MediaType.ALL));
    }

    @Test
    public void getUserById_2() throws Exception {
        when(userService.getUserById(any())).thenThrow(new RuntimeException());

        mockMvc.perform(
                        get("/api/user/id/{id}", "7a9223a4-820e-42d8-922b-162cea9e5f6e")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.auth_user + ":" + this.password).getBytes()))
                        .accept(MediaType.ALL));
    }

    @Test
    public void deleteByEmail_2() throws Exception {
        when(userService.deleteUserByEmail(any())).thenThrow(new RuntimeException());

        mockMvc.perform(
                        delete("/api/user/email/{email}", user.getEmail())
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.auth_user + ":" + this.password).getBytes()))
                        .accept(MediaType.ALL));
    }

    @Test
    public void deleteById_2() throws Exception {
        when(userService.deleteUserById(any(UUID.class))).thenThrow(new RuntimeException());

        mockMvc.perform(
                        delete("/api/user/id/{id}", "7a9223a4-820e-42d8-922b-162cea9e5f6e")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.auth_user + ":" + this.password).getBytes()))
                        .accept(MediaType.ALL));
    }

    @Test
    public void createUser() throws Exception {
        when(userService.saveUser(any())).thenReturn(true);

        mockMvc.perform(
                        post("/api/user/")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.auth_user + ":" + this.password).getBytes()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Demo2\",\"lastName\": \"Test\",\"phoneNumber\": \"+12345670\",\"email\": \"dxt@gmail.com\",\"passportNumber\": \"A1211512278\",\"address\": \"test\",\"diet\": 1,\"rewardsMiles\": 0}"))
                        .andExpect(status().isOk());
    }

    @Test
    public void updateUser() throws Exception {
        when(userService.saveUser(any())).thenReturn(true);

        mockMvc.perform(
                        put("/api/user/")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.auth_user + ":" + this.password).getBytes()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": \"7a9223a4-820e-42d8-922b-162cea9e5f6e\",\"firstName\": \"Demo2\",\"lastName\": \"Test\",\"phoneNumber\": \"+12345670\",\"email\": \"dxt@gmail.com\",\"passportNumber\": \"A1211512278\",\"address\": \"test\",\"diet\": 1,\"rewardsMiles\": 0}"))
                        .andExpect(status().isOk());
    }

    @Test
    public void createUser_2() throws Exception {
        when(userService.saveUser(any())).thenThrow(new RuntimeException());

        mockMvc.perform(
                        post("/api/user/")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.auth_user + ":" + this.password).getBytes()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Demo2\",\"lastName\": \"Test\",\"phoneNumber\": \"+12345670\",\"email\": \"dxt@gmail.com\",\"passportNumber\": \"A1211512278\",\"address\": \"test\",\"diet\": 1,\"rewardsMiles\": 0}"));
    }

    @Test
    public void updateUser_2() throws Exception {
        when(userService.saveUser(any())).thenThrow(new RuntimeException());

        mockMvc.perform(
                        put("/api/user/")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((this.auth_user + ":" + this.password).getBytes()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": \"7a9223a4-820e-42d8-922b-162cea9e5f6e\",\"firstName\": \"Demo2\",\"lastName\": \"Test\",\"phoneNumber\": \"+12345670\",\"email\": \"dxt@gmail.com\",\"passportNumber\": \"A1211512278\",\"address\": \"test\",\"diet\": 1,\"rewardsMiles\": 0}"));
    }
}