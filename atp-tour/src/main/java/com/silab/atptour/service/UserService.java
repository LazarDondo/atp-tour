package com.silab.atptour.service;

import com.silab.atptour.entity.User;
import com.silab.atptour.exceptions.AtpEntityExistsException;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Represents a service containing all the logic for managing user data
 * 
 * @author Lazar
 */
public interface UserService extends UserDetailsService {

    /**
     * Gets the user with the given credentials from the database
     * 
     * @param username A string representing user's username
     * @param password A string representing user's password
     * 
     * @return A {@link User} with the given credentials
     * 
     * @throws AtpEntityNotFoundException If there's no {@link User} under the given credentials to be found
     */
    public User login(String username, String password) throws AtpEntityNotFoundException;

    /**
     * Adds a new {@link User} to the database
     * 
     * @param user A {@link User} object to be added
     * 
     * @return An added {@link User}
     * 
     * @throws AtpEntityExistsException If a {@link User} with same username already exists in the database
     */
    public User register(User user) throws AtpEntityExistsException;

    /**
     * Adds user's data in the database
     * 
     * @param user A {@link User} object to be updated
     * 
     * @return An updated {@link User}
     * 
     * @throws AtpEntityNotFoundException If the given {@link User} doesn't exist in the database
     * @throws AtpEntityExistsException If a {@link User} with same username already exists in the database
     */
    public User updateUser(User user) throws AtpEntityNotFoundException, AtpEntityExistsException;
}
