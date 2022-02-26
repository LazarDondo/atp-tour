package com.silab.atptour.dao;

import com.silab.atptour.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents repository for user data management
 * 
 * @author Lazar
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    /**
     * Finds a user by username
     * 
     * @param username A string representing user's username
     * 
     * @return An {@link Optional} user
     */
    public Optional<User> findUserByUsername(String username);
}
