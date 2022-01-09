package com.silab.atptour.dao;

import com.silab.atptour.entity.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Lazar
 */
@DataJpaTest
public class CountryDaoTest {
    
    @Autowired
    CountryDao countryDao;
        
    @Test
    public void saveCountryShouldBeOk(){
        Country country = new Country(1, "Serbia", "SRB");
        assertEquals(country, countryDao.save(country));
    }
    
}
