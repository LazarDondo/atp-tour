package com.silab.atptour.service.impl;

import com.silab.atptour.dao.CountryDao;
import com.silab.atptour.entity.Country;
import com.silab.atptour.service.CountryService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Represent an implementation of the {@link CountryService} interface
 * 
 * @author Lazar
 */
@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryDao countryDao;

    private final Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Country> getCountries(String name) {
        logger.debug("Finding all countries");
        return countryDao.findByNameContainingIgnoreCase(name);
    }
}
