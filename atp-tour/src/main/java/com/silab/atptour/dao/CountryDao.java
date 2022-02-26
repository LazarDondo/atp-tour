package com.silab.atptour.dao;

import com.silab.atptour.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Represents repository for country data management
 *
 * @author Lazar
 */
public interface CountryDao extends JpaRepository<Country, Long> {

}
