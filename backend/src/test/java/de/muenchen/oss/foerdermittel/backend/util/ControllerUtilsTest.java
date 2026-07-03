package de.muenchen.oss.foerdermittel.backend.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

class ControllerUtilsTest {

    @Test
    void testConvertStringToBigDecimal_validInput() {
        String input = "123";
        BigDecimal result = ControllerUtils.convertStringToBigDecimal(input);
        assertEquals(BigDecimal.valueOf(123), result);
    }

    @Test
    void testConvertStringToBigDecimal_invalidInput() {
        String input = "abc";
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            ControllerUtils.convertStringToBigDecimal(input);
        });
        assertTrue(exception.getReason().contains("Invalid ID"));
    }
}