package com.flightman.flightmanapi.controller;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightman.flightmanapi.model.User;
import com.flightman.flightmanapi.repositories.UserRepository;
import com.flightman.flightmanapi.services.UserService;

@RestController
public class UserController {

        @Autowired
        private UserService userService;
        private UserRepository userRepository;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		try {
                        List<User> usersList = userService.getAllUsers();
                        if(usersList.size() > 0)
                                return new ResponseEntity<>(usersList, HttpStatus.OK);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.out));
                        System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

        @GetMapping("/user/id/{id}")
        public ResponseEntity<User> getUserById(@PathVariable("id") UUID id) {
                try {
                        User user = this.userService.getUserById(id);

                        if(user != null)
                                return new ResponseEntity<>(user, HttpStatus.OK);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.out));
                        System.out.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @GetMapping("/user/email/{email}")
        public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
                try {
                        User user = this.userService.getUserByEmail(email);

                        if(user != null)
                                return new ResponseEntity<>(user, HttpStatus.OK);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.out));
                        System.out.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @PostMapping("/user")
        public ResponseEntity<UUID> createUser(@RequestBody User user)   
        {  
                try {
                        if(this.userService.saveUser(user))
                                return new ResponseEntity<>(user.getID(), HttpStatus.OK);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.out));
                        System.out.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @PutMapping("/user")
        public ResponseEntity<Boolean> updateUser(@RequestBody User user)   
        {  
                try {
                        if(this.userService.saveUser(user))
                                return new ResponseEntity<>(true, HttpStatus.OK);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.out));
                        System.out.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @DeleteMapping("/user/id/{id}")
        public ResponseEntity<Boolean> deleteUserById(@PathVariable("id") UUID id) {
                try {
                        if(this.userService.deleteUserById(id))
                                return new ResponseEntity<>(true, HttpStatus.OK);
                        return new ResponseEntity<>(false, HttpStatus.OK);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.out));
                        System.out.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @DeleteMapping("/user/email/{email}")
        public ResponseEntity<Boolean> deleteUserByEmail(@PathVariable("email") String email) {
                try {
                        if(this.userService.deleteUserByEmail(email))
                                return new ResponseEntity<>(true, HttpStatus.OK);
                        return new ResponseEntity<>(false, HttpStatus.OK);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.out));
                        System.out.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }
}
