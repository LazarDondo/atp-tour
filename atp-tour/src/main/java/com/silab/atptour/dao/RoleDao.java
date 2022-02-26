package com.silab.atptour.dao;

import com.silab.atptour.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lazar
 */
public interface RoleDao extends JpaRepository<Role, Long> {

    public Optional<Role> findRoleByName(String name);
}
