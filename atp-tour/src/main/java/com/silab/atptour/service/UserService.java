package com.silab.atptour.service;

import com.silab.atptour.entity.User;
import com.silab.atptour.exceptions.AtpEntityExistsException;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Lazar
 */
public interface UserService extends UserDetailsService{
    
    public User login(String username, String password) throws AtpEntityNotFoundException;
    
    public User register(User user) throws AtpEntityExistsException;
    
    public User updateUser(User user) throws AtpEntityNotFoundException, AtpEntityExistsException;
}
