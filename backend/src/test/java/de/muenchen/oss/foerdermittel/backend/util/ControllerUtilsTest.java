package de.muenchen.oss.foerdermittel.backend.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class ControllerUtilsTest {

    @Test
    void givenValidStringInput_thenConversionSucceeds() {
        String input = "123";
        BigDecimal result = ControllerUtils.convertStringToBigDecimal(input);
        assertThat(result, is(BigDecimal.valueOf(123)));
    }

    @Test
    void givenInvalidStringInput_thenConversionFails() {
        String input = "abc";
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            ControllerUtils.convertStringToBigDecimal(input);
        });
        assertThat(exception.getReason(), containsString("Invalid ID"));
    }

}
