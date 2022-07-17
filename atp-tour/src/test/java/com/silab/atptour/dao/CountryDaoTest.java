package com.silab.atptour.dao;

import com.silab.atptour.entity.Country;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Lazar
 */
@DataJpaTest
public class CountryDaoTest {

    private Country firstTestCountry;
    private Country secondTestCountry;

    @Autowired
    private CountryDao countryDao;

    @BeforeEach
    public void init() {
        firstTestCountry = countryDao.save(new Country(1, "Serbia", "SRB"));
        secondTestCountry = countryDao.save(new Country(2, "Russia", "RUS"));
    }

    @Test
    public void getCountriesShouldBeOk() {
        List<Country> countries = new ArrayList<>();
        countries.add(firstTestCountry);
        countries.add(secondTestCountry);
        assertEquals(countries, countryDao.findAll());
    }
    
    @Test
    public void getCountriesByNameShouldBeOk() {
        List<Country> countries = new ArrayList<>();
        countries.add(firstTestCountry);
        countries.add(secondTestCountry);
        assertEquals(countries, countryDao.findByNameContainingIgnoreCase("a"));
        assertEquals(firstTestCountry, countryDao.findByNameContainingIgnoreCase("S").get(0));
    }

}
