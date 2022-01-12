package com.silab.atptour.controller;

import com.silab.atptour.entity.User;
import com.silab.atptour.exceptions.AtpEntityExistsException;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Lazar
 */
@RestController
@RequestMapping("user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody User user) {
        logger.info("Login for user {}", user.getUsername());
        try {
            User loggedUser = userService.login(user.getUsername(), user.getPassword());
            logger.info("User {} {} has logged in successfully", loggedUser.getFirstName(), loggedUser.getLastName());
            return ResponseEntity.ok(loggedUser);
        } catch (AtpEntityNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody User user) {
        logger.info("Registering user {} {}", user.getFirstName(), user.getLastName());
        User registeredUser;
        try {
            registeredUser = userService.register(user);
            logger.info("Successfully registered user");
            return ResponseEntity.ok(registeredUser);
        } catch (AtpEntityExistsException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        logger.info("Updating user {}", user.getUsername());
        try {
            User loggedUser = userService.updateUser(user);
            logger.info("User {} {} has been updated successfully", loggedUser.getFirstName(), loggedUser.getLastName());
            return ResponseEntity.ok(loggedUser);
        } catch (AtpEntityNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (AtpEntityExistsException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
