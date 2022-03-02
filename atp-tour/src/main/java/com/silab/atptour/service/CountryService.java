package com.silab.atptour.service;

import com.silab.atptour.entity.Country;
import java.util.List;

/**
 * Represents a service containing all the logic for country data management
 *
 * @author Lazar
 */
public interface CountryService {

    /**
     * Retrieves all countries from the database
     *
     * @return A {@link List} of countries
     */
    public List<Country> getCountries();
}
