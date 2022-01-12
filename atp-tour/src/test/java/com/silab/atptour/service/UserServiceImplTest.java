package com.silab.atptour.service;

import com.silab.atptour.dao.RoleDao;
import com.silab.atptour.dao.UserDao;
import com.silab.atptour.entity.Role;
import com.silab.atptour.entity.User;
import com.silab.atptour.exceptions.AtpEntityExistsException;
import com.silab.atptour.exceptions.AtpEntityNotFoundException;
import com.silab.atptour.security.MyUserDetails;
import com.silab.atptour.service.impl.UserServiceImpl;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Lazar
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    RoleDao roleDao;

    @Mock
    UserDao userDao;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Value("${default.user.role}")
    private static String defaultUserRole;
    private static Role testRole;
    private static User testUser;
    private static Optional<User> optionalUser;
    private static Optional<User> emptyUser;

    @BeforeAll
    public static void init() {
        Set<Role> roles = new HashSet<>();
        testRole = new Role(1, defaultUserRole);
        roles.add(testRole);
        testUser = new User(1, "homersimpson@gmail.com", "maxpower", "Homer", "Simpson", true, roles);
        optionalUser = Optional.of(testUser);
        emptyUser = Optional.empty();
    }

    @Test
    public void loginShouldBeOk() throws AtpEntityNotFoundException {
        when(userDao.findUserByUsername(testUser.getUsername())).thenReturn(optionalUser);
        when(passwordEncoder.matches(testUser.getPassword(), "$2a$12$Op8pBz.2fB3bBxNwZur7IudXzw3l1cIuES4fvNbD0ufghzlaWviee")).thenReturn(true);
        assertEquals(testUser, userService.login(testUser.getUsername(), testUser.getPassword()));
    }

    @Test
    public void loginShouldThrowAtpEntityNotFoundException() {
        when(userDao.findUserByUsername(testUser.getUsername())).thenReturn(emptyUser);
        Assertions.assertThrows(AtpEntityNotFoundException.class, () -> userService.login(testUser.getUsername(), testUser.getPassword()));
    }

    @Test
    public void registerShouldBeOk() throws AtpEntityExistsException {
        when(userDao.findUserByUsername(testUser.getUsername())).thenReturn(emptyUser);
        when(roleDao.findRoleByName(defaultUserRole)).thenReturn(Optional.of(testRole));
        when(passwordEncoder.encode(testUser.getPassword())).thenReturn("$2a$12$Op8pBz.2fB3bBxNwZur7IudXzw3l1cIuES4fvNbD0ufghzlaWviee");
        when(userDao.save(testUser)).thenReturn(testUser);
        assertEquals(testUser, userService.register(testUser));
    }

    @Test
    public void registerShouldThrowAtpEntityExistsException() {
        when(userDao.findUserByUsername(testUser.getUsername())).thenReturn(optionalUser);
        Assertions.assertThrows(AtpEntityExistsException.class, () -> userService.register(testUser));
    }

    @Test
    public void updateUserShouldBeOk() throws AtpEntityNotFoundException, AtpEntityExistsException {
        when(userDao.findById(testUser.getId())).thenReturn(optionalUser);
        when(passwordEncoder.encode(testUser.getPassword())).thenReturn(" $2a$12$Op8pBz.2fB3bBxNwZur7IudXzw3l1cIuES4fvNbD0ufghzlaWviee");
        when(userDao.save(testUser)).thenReturn(testUser);
        assertEquals(testUser, userService.updateUser(testUser));
    }

    @Test
    public void updateUserShouldThrowAtpEntityNotFoundException() {
        when(userDao.findById(testUser.getId())).thenReturn(emptyUser);
        Assertions.assertThrows(AtpEntityNotFoundException.class, () -> userService.updateUser(testUser));
    }

    @Test
    public void updateUserShouldThrowAtpEntityExistsException() {
        User user = new User(1, "bartsimpson@gmail.com", "elbarto", "Bart", "Simpson", true, null);
        when(userDao.findById(user.getId())).thenReturn(optionalUser);
        when(userDao.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        Assertions.assertThrows(AtpEntityExistsException.class, () -> userService.updateUser(user));
    }

    @Test
    public void loadUserByUsernameShouldBeOk() {
        MyUserDetails userDetails = new MyUserDetails(testUser);
        when(userDao.findUserByUsername(testUser.getUsername())).thenReturn(optionalUser);
        assertEquals(userDetails, userService.loadUserByUsername(testUser.getUsername()));
    }

    @Test
    public void loadUserByUsernameShouldAtpThrowUsernameNotFoundException() {
        when(userDao.findUserByUsername(testUser.getUsername())).thenReturn(emptyUser);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(testUser.getUsername()));
    }

}
