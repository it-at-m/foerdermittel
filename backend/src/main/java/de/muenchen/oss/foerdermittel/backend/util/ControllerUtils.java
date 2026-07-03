package de.muenchen.oss.foerdermittel.backend.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

public class ControllerUtils {

    public static BigDecimal convertStringToBigDecimal(final String value) {
        try {
            return BigDecimal.valueOf(Integer.parseInt(value));
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID: " + value, ex);
        }
    }
}
