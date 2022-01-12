package com.silab.atptour.dao;

import com.silab.atptour.entity.Role;
import com.silab.atptour.entity.User;
import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Lazar
 */
@DataJpaTest
public class UserDaoTest {
    
    @Autowired
    RoleDao roleDao;
    
    @Autowired
    UserDao userDao;
    
    private Set<Role> testRoles;
    private User testUser;    
    
    @BeforeEach
    public void init() {
        testRoles = new HashSet<>();
        testRoles.add(roleDao.save(new Role(1, "USER")));
        testUser = userDao.save(new User(1, "admin@atp.com", "admin", "Bart", "Simpson", true, testRoles));
    }
    
    @Test
    public void addUserShouldBeOk() {
        User user = new User(1, "homersimpson@gmail.com", "maxpower", "Homer", "Simpson", true, testRoles);
        assertEquals(user, userDao.save(user));
    }
    
    @Test
    public void findUserByIdShouldBeOk() {
        assertEquals(testUser, userDao.findById(testUser.getId()).get());
    }
    
    @Test
    public void findUserByUsernameShouldNotFindUser() {
        assertEquals(true, userDao.findById(55L).isEmpty());
    }
    
    @Test
    public void updateUserShouldBeOK() {
        testUser.setUsername("bartsimpson@gmail.com");
        assertEquals(testUser, userDao.save(testUser));
    }
    
}
