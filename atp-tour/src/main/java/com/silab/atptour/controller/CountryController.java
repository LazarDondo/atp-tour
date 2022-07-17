package com.silab.atptour.controller;

import com.silab.atptour.entity.Country;
import com.silab.atptour.service.CountryService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for country data management
 * 
 * @author Lazar
 */
@RestController
@RequestMapping("country")
@CrossOrigin(origins = "*")
public class CountryController {

    @Autowired
    private CountryService countryService;

    private final Logger logger = LoggerFactory.getLogger(CountryController.class);

   /**
    * GET request for retrieving all countries from the database
    * 
    * @param name A string representing name of the country
    * 
    * @return A {@link ResponseEntity} instance with found countries and OK HTTP status 
    */
    @GetMapping
    public ResponseEntity<List<Country>> getCountries( @RequestParam(required = false, defaultValue = "") String name) {
        List<Country> countries = countryService.getCountries(name);
        logger.info("Successfully retrieved {} countries", countries.size());
        return ResponseEntity.ok(countries);
    }
}
