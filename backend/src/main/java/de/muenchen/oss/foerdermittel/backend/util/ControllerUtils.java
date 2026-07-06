package de.muenchen.oss.foerdermittel.backend.util;

import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class ControllerUtils {

    private ControllerUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static BigDecimal convertStringToBigDecimal(final String value) {
        try {
            return BigDecimal.valueOf(Integer.parseInt(value));
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID: " + value, ex);
        }
    }
}
