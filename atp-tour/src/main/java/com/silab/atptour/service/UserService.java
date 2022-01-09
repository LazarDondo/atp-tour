package com.silab.atptour.service;

import com.silab.atptour.entity.User;
import com.silab.atptour.exceptions.EntityNotFoundException;

/**
 *
 * @author Lazar
 */
public interface UserService {
    
    public User login(String username, String password) throws EntityNotFoundException;
    
    public User register(User user);
    
    public User updateUser(User user) throws EntityNotFoundException;
}
