package de.muenchen.oss.foerdermittel.backend.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class ControllerUtilsTest {

    @Test
    void givenValidStringInput_thenConversionSucceeds() {
        String input = "123";
        BigDecimal result = ControllerUtils.convertStringToBigDecimal(input);
        assertEquals(BigDecimal.valueOf(123), result);
    }

    @Test
    void givenInvalidStringInput_thenConversionFails() {
        String input = "abc";
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            ControllerUtils.convertStringToBigDecimal(input);
        });
        assertTrue(exception.getReason().contains("Invalid ID"));
    }
}
