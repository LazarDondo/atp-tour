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

    private Country testCountry;

    @Autowired
    private CountryDao countryDao;

    @BeforeEach
    public void init() {
        testCountry = countryDao.save(new Country(1, "Serbia", "SRB"));
    }

    @Test
    public void getCountriesShouldBeOk() {
        Country country = countryDao.save(new Country(2, "Russia", "RUS"));
        List<Country> countries = new ArrayList<>();
        countries.add(testCountry);
        countries.add(country);
        assertEquals(countries, countryDao.findAll());
    }

}
