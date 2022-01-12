package com.silab.atptour.dao;

import com.silab.atptour.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lazar
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
       
    public Optional<User> findUserByUsername(String username);
}
