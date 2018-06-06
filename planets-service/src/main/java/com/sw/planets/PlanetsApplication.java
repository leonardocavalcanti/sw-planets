package com.sw.planets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class PlanetsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanetsApplication.class, args);
    }
}
