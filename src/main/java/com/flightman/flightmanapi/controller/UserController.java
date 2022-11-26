package com.flightman.flightmanapi.controller;

import java.util.UUID;

import javax.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightman.flightmanapi.model.User;
import com.flightman.flightmanapi.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/api")
@RestController
@Api(description = "Set of endpoints for Creating, Finding, and Deleting Users.")
public class UserController {

        @Autowired
        private UserService userService;

        @ApiOperation(value = "Get All Users", notes = "Returns the details of all the users")
        @ApiResponses({ @ApiResponse(code = 200, message = "User details are successfully retrieved"),
                        @ApiResponse(code = 400, message = "No users were found oon the server"),
                        @ApiResponse(code = 500, message = "There was an unexpected problem during user detail retrieval") })
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		try {
                        List<User> usersList = userService.getAllUsers();
                        if(usersList.size() > 0)
                                return new ResponseEntity<>(usersList, HttpStatus.OK);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.err));
                        System.err.println(e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

        @ApiOperation(value = "Get User By ID", notes = "Returns the details of the supplied user based on their ID (if it exists)")
        @ApiResponses({ @ApiResponse(code = 200, message = "User details are successfully retrieved"),
                        @ApiResponse(code = 400, message = "The supplied user was not found on the server"),
                        @ApiResponse(code = 500, message = "There was an unexpected problem during user detail retrieval") })
        @GetMapping("/user/id/{id}")
        public ResponseEntity<User> getUserById(@PathVariable("id") UUID id) {
                try {
                        User user = this.userService.getUserById(id);

                        if(user != null)
                                return new ResponseEntity<>(user, HttpStatus.OK);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.err));
                        System.err.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @ApiOperation(value = "Get User By Email", notes = "Returns the details of the supplied user based on their Email (if it exists)")
        @ApiResponses({ @ApiResponse(code = 200, message = "User details are successfully retrieved"),
                        @ApiResponse(code = 400, message = "The supplied user was not found on the server"),
                        @ApiResponse(code = 500, message = "There was an unexpected problem during user detail retrieval") })
        @GetMapping("/user/email/{email}")
        public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
                try {
                        User user = this.userService.getUserByEmail(email);

                        if(user != null)
                                return new ResponseEntity<>(user, HttpStatus.OK);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.err));
                        System.err.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @ApiOperation(value = "Create User", notes = "Creates a new user and returns a unique ID")
        @ApiResponses({ @ApiResponse(code = 200, message = "A new user was successfully created"),
                        @ApiResponse(code = 400, message = "The user already exists"),
                        @ApiResponse(code = 500, message = "There was an unexpected problem during user creation") })
        @PostMapping("/user")
        public ResponseEntity<UUID> createUser(@RequestBody User user)   
        {  
                try {
                        if(this.userService.saveUser(user))
                                return new ResponseEntity<>(user.getID(), HttpStatus.OK);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.err));
                        System.err.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @ApiOperation(value = "Updated User", notes = "Update a user's details")
        @ApiResponses({ @ApiResponse(code = 200, message = "The user was successfully updated"),
                        @ApiResponse(code = 400, message = "The supplied user was not found on the server"),
                        @ApiResponse(code = 500, message = "There was an unexpected problem during user updation") })
        @PutMapping("/user")
        public ResponseEntity<Boolean> updateUser(@RequestBody User user)   
        {  
                try {
                        if(this.userService.saveUser(user))
                                return new ResponseEntity<>(true, HttpStatus.OK);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.err));
                        System.err.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @ApiOperation(value = "Delete User By ID", notes = "Deletes a user based on their ID")
        @ApiResponses({ @ApiResponse(code = 200, message = "The user was successfully deleted"),
                        @ApiResponse(code = 400, message = "The supplied user was not found on the server"),
                        @ApiResponse(code = 500, message = "There was an unexpected problem during user deletion") })
        @Transactional
        @DeleteMapping("/user/id/{id}")
        public ResponseEntity<Boolean> deleteUserById(@PathVariable("id") UUID id) {
                try {
                        if(this.userService.deleteUserById(id))
                                return new ResponseEntity<>(true, HttpStatus.OK);
                        return new ResponseEntity<>(false, HttpStatus.OK);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.err));
                        System.err.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @ApiOperation(value = "Delete User By Email", notes = "Deletes a user based on their Email")
        @ApiResponses({ @ApiResponse(code = 200, message = "The user was successfully deleted"),
                        @ApiResponse(code = 400, message = "The supplied user was not found on the server"),
                        @ApiResponse(code = 500, message = "There was an unexpected problem during user deletion") })
        @Transactional
        @DeleteMapping("/user/email/{email}")
        public ResponseEntity<Boolean> deleteUserByEmail(@PathVariable("email") String email) {
                try {
                        if(this.userService.deleteUserByEmail(email))
                                return new ResponseEntity<>(true, HttpStatus.OK);
                        return new ResponseEntity<>(false, HttpStatus.OK);
                } catch (Exception e) {
                        e.printStackTrace(new java.io.PrintStream(System.err));
                        System.err.println(e);
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }
}
