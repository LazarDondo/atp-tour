package com.silab.atptour.controller;

import com.silab.atptour.entity.Country;
import com.silab.atptour.service.CountryService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Lazar
 */
@RestController
@RequestMapping("country")
public class CountryController {

    private final Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    CountryService countryService;

    @GetMapping
    public ResponseEntity<List<Country>> getCountries() {
        List<Country> countries = countryService.getCountries();
        logger.info("Successfully retrieved {} countries", countries.size());
        return ResponseEntity.ok(countries);
    }
}
