package com.silab.atptour.dao;

import com.silab.atptour.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Lazar
 */
@DataJpaTest
public class RoleDaoTest {
    
    @Autowired
    private RoleDao roleDao;
    
    private Role testRole;
    
    @BeforeEach
    public void init(){
        testRole = roleDao.save(new Role(1, "USER"));
    }
    
    @Test
    public void saveRoleShouldBeOk(){
        Role role = new Role(2, "ADMIN");
        assertEquals(role, roleDao.save(role));
    }
    
    @Test
    public void findRoleByNameShouldBeOk(){
        assertEquals(testRole, roleDao.findRoleByName(testRole.getName()).get());
    }
    
    @Test
    public void findRoleByNameShouldNotReturnRole(){
        assertEquals(true, roleDao.findRoleByName("TEST").isEmpty());
    }
}
