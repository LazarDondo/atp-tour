package com.silab.atptour.service;

import com.silab.atptour.dao.CountryDao;
import com.silab.atptour.entity.Country;
import com.silab.atptour.service.impl.CountryServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Lazar
 */
@ExtendWith(MockitoExtension.class)
public class CountryServiceImplTest {

    @Mock
    CountryDao countryDao;

    @InjectMocks
    CountryServiceImpl countryService;

    @Test
    public void getCountriesShouldBeOk() {
        List<Country> countries = new ArrayList<>() {
            {
                new Country(1, "Serbia", "SRB");
                new Country(2, "Greece", "GRE");
            }
        };

        when(countryDao.findAll()).thenReturn(countries);
        assertEquals(countries, countryService.getCountries());
    }
}
