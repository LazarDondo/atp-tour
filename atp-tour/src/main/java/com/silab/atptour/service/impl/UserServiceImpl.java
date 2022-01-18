package com.silab.atptour.service.impl;

import com.silab.atptour.security.MyUserDetails;
import com.silab.atptour.dao.RoleDao;
import com.silab.atptour.dao.UserDao;
import com.silab.atptour.entity.Role;
import com.silab.atptour.entity.User;
import com.silab.atptour.exceptions.AtpEntityExistsException;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.service.UserService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${default.user.role}")
    private String defaultUserRole;

    @Override
    public User login(String username, String password) throws AtpEntityNotFoundException {
        logger.debug("Login for  user with username {} ", username);
        Optional<User> optionalUser = userDao.findUserByUsername(username);
        if (optionalUser.isEmpty() || !passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            throw new AtpEntityNotFoundException("User with the given username or password doesn't exist");
        }
        return optionalUser.get();
    }

    @Override
    public User register(User user) throws AtpEntityExistsException {
        logger.debug("Adding new user {}", user.getUsername());
        if (userDao.findUserByUsername(user.getUsername()).isPresent()) {
            throw new AtpEntityExistsException("User with username " + user.getUsername() + " already exists");
        }
        Set<Role> roles = getUserRoles(defaultUserRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        return userDao.save(user);
    }

    @Override
    public User updateUser(User user) throws AtpEntityNotFoundException, AtpEntityExistsException {
        logger.debug("Finding user {}", user.getUsername());
        Optional<User> optionalUser = userDao.findById(user.getId());
        if (optionalUser.isEmpty()) {
            throw new AtpEntityNotFoundException("User doesn't exist");
        }
        if (!optionalUser.get().getUsername().equals(user.getUsername()) && userDao.findUserByUsername(user.getUsername()).isPresent()){
            throw new AtpEntityExistsException("User with username " + user.getUsername() + " already exists");
        }
        user.setPassword(optionalUser.get().getPassword());
        user.setRoles(optionalUser.get().getRoles());
        return userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userDao.findUserByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User couldn't be found");
        }
        return new MyUserDetails(optionalUser.get());
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
        roles.add(role);
        return roles;
    }

}
