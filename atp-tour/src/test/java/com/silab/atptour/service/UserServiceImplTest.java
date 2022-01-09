package com.silab.atptour.service;

import com.silab.atptour.dao.RoleDao;
import com.silab.atptour.dao.UserDao;
import com.silab.atptour.entity.Role;
import com.silab.atptour.entity.User;
import com.silab.atptour.exceptions.EntityNotFoundException;
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
    public void loginShouldBeOk() throws EntityNotFoundException {
        when(userDao.findUserByUsernameAndPassword(testUser.getUsername(), testUser.getPassword())).thenReturn(optionalUser);
        assertEquals(testUser, userService.login(testUser.getUsername(), testUser.getPassword()));
    }

    @Test
    public void loginShouldThrowEntityNotFoundException() {
        when(userDao.findUserByUsernameAndPassword(testUser.getUsername(), testUser.getPassword())).thenReturn(emptyUser);
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.login(testUser.getUsername(), testUser.getPassword()));
    }
    
    @Test
    public void registerShouldBeOk(){
        when(roleDao.findRoleByName(defaultUserRole)).thenReturn(Optional.of(testRole));
        when(userDao.save(testUser)).thenReturn(testUser);
        assertEquals(testUser, userService.register(testUser));
    }
    
    @Test
    public void updateUserShouldBeOk() throws EntityNotFoundException{
        when(userDao.findById(testUser.getId())).thenReturn(optionalUser);
        when(userDao.save(testUser)).thenReturn(testUser);
        assertEquals(testUser, userService.updateUser(testUser));
    }
    
    @Test
    public void updateUserShouldThrowEntityNotFoundException(){
        when(userDao.findById(testUser.getId())).thenReturn(emptyUser);
        Assertions.assertThrows(EntityNotFoundException.class, ()->userService.updateUser(testUser));
    }

}
