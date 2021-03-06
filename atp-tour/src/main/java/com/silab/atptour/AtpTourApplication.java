package com.silab.atptour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Class with main method
 * 
 * @author Lazar
 */
@EnableScheduling
@SpringBootApplication
public class AtpTourApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtpTourApplication.class, args);
    }

}
