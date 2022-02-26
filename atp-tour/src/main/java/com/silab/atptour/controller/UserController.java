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
 * Rest controller for user data management
 * 
 * @author Lazar
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * POST request for user login
     * 
     * @param user A {@link User} object with credentials for logging in
     * 
     * @return 
    *      <ul>
     *          <li>A {@link ResponseEntity} instance with the logged user and OK HTTP status if the user has been found</li>
     *          <li>A {@link ResponseEntity} instance with error message and NOT_FOUND HTTP status if the user doesn't exist</li>
     *      </ul> 
     */
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

    /**
     * POST request for registering new user
     *
     * @param user A {@link User} object to be registered
     *
     * @return
     *      <ul>
     *          <li>A {@link ResponseEntity} instance with the registered user and OK HTTP status if the user has been registered successfully</li>
     *          <li>A {@link ResponseEntity} instance with error message and CONFLICT HTTP status if a user with the same name already exists</li>
     *      </ul>       
     */
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

    /**
     * PUT request for updating user's data
     *
     * @param user A {@link User} object to be updated
     *
     * @return
     *      <ul>
     *          <li>A {@link ResponseEntity} instance with the updated user and OK HTTP status if the user has been updated successfully</li>
     *          <li>A {@link ResponseEntity} instance with error message and NOT_FOUND HTTP status if the user doesn't exist</li>
     *          <li>A {@link ResponseEntity} instance with error message and CONFLICT HTTP status if a user with the same username already exists</li>
     *      </ul>       
     */
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
