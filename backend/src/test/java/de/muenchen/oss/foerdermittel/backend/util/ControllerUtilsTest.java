package de.muenchen.oss.foerdermittel.backend.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class ControllerUtilsTest {

    @Test
    void givenValidStringInput_thenConversionSucceeds() {
        String input = "123";
        BigDecimal result = ControllerUtils.convertStringToBigDecimal(input);
        assertThat(result).isEqualTo(BigDecimal.valueOf(123));
    }

    @Test
    void givenInvalidStringInput_thenConversionFails() {
        String input = "abc";
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            ControllerUtils.convertStringToBigDecimal(input);
        });
        assertThat(exception.getMessage()).isEqualTo(String.format(String.format("400 BAD_REQUEST \"Invalid ID: %s\"", input)));
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
