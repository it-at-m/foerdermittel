package de.muenchen.oss.foerdermittel.backend.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class NumberMapperTest {

    @Test
    void givenNullInput_thenReturnNull() {
        // given
        final BigDecimal input = null;

        // when
        final String result = NumberMapper.bigDecimalToIntegerString(input);

        // then
        assertThat(result).isNull();
    }

    @Test
    void givenDecimalInput_thenRemoveDecimalPoints() {
        // given
        final BigDecimal input = new BigDecimal("10.7");

        // when
        final String result = NumberMapper.bigDecimalToIntegerString(input);

        // then
        assertThat(result).isEqualTo("10");
    }

    @Test
    void givenWholeNumber_thenConvertToString() {
        // given
        final BigDecimal input = new BigDecimal("42");

        // when
        final String result = NumberMapper.bigDecimalToIntegerString(input);

        // then
        assertThat(result).isEqualTo("42");
    }
}
