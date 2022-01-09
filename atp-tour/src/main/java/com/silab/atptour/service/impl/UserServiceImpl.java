package com.silab.atptour.service.impl;

import com.silab.atptour.dao.RoleDao;
import com.silab.atptour.dao.UserDao;
import com.silab.atptour.entity.Role;
import com.silab.atptour.entity.User;
import com.silab.atptour.exceptions.EntityNotFoundException;
import com.silab.atptour.service.UserService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lazar
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${default.user.role}")
    private String defaultUserRole;

    @Override
    public User login(String username, String password) throws EntityNotFoundException {
        logger.debug("Login for  user with username {} ", username);
        Optional<User> optionalUser = userDao.findUserByUsernameAndPassword(username, password);
        if (optionalUser.isEmpty()) {
            logger.error("User with the given username or password doesn't exist");
            throw new EntityNotFoundException("Wrong username or password");
        }
        return optionalUser.get();
    }

    @Override
    public User register(User user) {
        logger.debug("Adding new user {}", user.getUsername());
        Set<Role> roles = getUserRoles(defaultUserRole);
        user.setRoles(roles);
        return userDao.save(user);
    }

    @Override
    public User updateUser(User user) throws EntityNotFoundException {
        logger.debug("Finding user {}", user.getUsername());
        Optional<User> optionalUser = userDao.findById(user.getId());

        if (optionalUser.isEmpty()) {
            logger.error("User with username {} doesn't exist", user.getUsername());
            throw new EntityNotFoundException("User with username " + user.getUsername() + " doesn't exist");
        }
        return userDao.save(user);
    }

    private Set<Role> getUserRoles(String defaultUser) {
        Role role = new Role();
        Optional<Role> optionalRole = roleDao.findRoleByName(defaultUser);
        if (optionalRole.isEmpty()) {
            role.setName(defaultUser);
            role = roleDao.save(role);
        } else {
            role = optionalRole.get();
        }
        Set<Role> roles = new HashSet<>();
        roles.add(optionalRole.get());
        return roles;
    }

}
