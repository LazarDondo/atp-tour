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
     * Retrieves countries from the database
     * 
     * @param name A string representing name of the country
     *
     * @return A {@link List} of countries
     */
    public List<Country> getCountries(String name);
}
