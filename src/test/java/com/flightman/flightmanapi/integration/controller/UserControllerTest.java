package com.flightman.flightmanapi.integration.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.flightman.flightmanapi.controller.UserController;
import com.flightman.flightmanapi.model.User;
import com.flightman.flightmanapi.services.UserService;

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

        User user = new User("FN", "LN", "1", "EM", "PN", "AD", 1, 0);

        private String auth_user = "abhilash";
        private String password = "securedpasswordofsrishti";

        @Test
        public void getUser() throws Exception {
                List<User> created = new ArrayList<User>();
                created.add(user);
                given(userService.getAllUsers()).willReturn(created);
                mockMvc.perform(
                                get("/api/users")
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.auth_user + ":" + this.password)
                                                                                                .getBytes()))
                                                .accept(MediaType.ALL))
                                .andExpect(status().isOk());
        }

        @Test
        public void getUserByEmail() throws Exception {
                given(userService.getUserByEmail(user.getEmail())).willReturn(user);
                mockMvc.perform(
                                get("/api/user/email/{email}", user.getEmail())
                                                .header(HttpHeaders.AUTHORIZATION,
                                                                "Basic " + Base64Utils.encodeToString(
                                                                                (this.auth_user + ":" + this.password)
                                                                                                .getBytes()))
                                                .accept(MediaType.ALL))
                                .andExpect(status().isOk());
        }

}