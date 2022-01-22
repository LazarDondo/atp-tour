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
 *
 * @author Lazar
 */
@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryDao countryDao;

    private final Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

    @Override
    public List<Country> getCountries() {
        logger.debug("Finding all countries");
        return countryDao.findAll();
    }
}
