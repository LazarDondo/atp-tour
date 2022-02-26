package com.silab.atptour.dao;

import com.silab.atptour.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Represents repository for role data management
 * 
 * @author Lazar
 */
public interface RoleDao extends JpaRepository<Role, Long> {

    /**
     * Finds a role by name
     * 
     * @param name A string representing the role name
     *  
     * @return An {@link Optional} role
     */
    public Optional<Role> findRoleByName(String name);
}
