package com.silab.atptour.dao;

import com.silab.atptour.entity.Country;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Represents repository for country data management
 *
 * @author Lazar
 */
public interface CountryDao extends JpaRepository<Country, Long> {
    
    /**
     * Find all countries by  name
     * 
     * @param name A string representing name of the country
     * 
     * @return A {@link List} of countries
     */
    @Query("SELECT c FROM Country c WHERE c.name LIKE %:name%")
    public List<Country> findByName(String name);
}
