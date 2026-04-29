package de.muenchen.oss.foerdermittel.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Application class for starting the microservice.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@SuppressWarnings("PMD.UseUtilityClass")
public class FoerdermittelBackendApplication {
    public static void main(final String[] args) {
        SpringApplication.run(FoerdermittelBackendApplication.class, args);
    }
}
