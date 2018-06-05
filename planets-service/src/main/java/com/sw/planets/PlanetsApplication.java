package com.sw.planets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableConfigurationProperties
@Configuration
public class PlanetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanetsApplication.class, args);
	}
}
