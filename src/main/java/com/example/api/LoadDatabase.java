package com.example.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.Month;


@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EventRepository repository){
        return args ->{
            log.info("Preloading " + repository.save(new Event("Lab PDM", LocalDateTime.of(2022, Month.OCTOBER, 21, 16, 0, 0), "FSEGA")));
            log.info("Preloading " + repository.save(new Event("Lab PPD", LocalDateTime.of(2022, Month.OCTOBER, 21, 8, 0, 0), "FSEGA")));
        };
    }
}
