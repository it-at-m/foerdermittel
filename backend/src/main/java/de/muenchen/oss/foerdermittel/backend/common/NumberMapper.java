package de.muenchen.oss.foerdermittel.backend.common;

import java.math.BigDecimal;
import org.mapstruct.Named;

public class NumberMapper {

    private NumberMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    @Named("bigDecimalToIntegerString")
    public static String bigDecimalToIntegerString(final BigDecimal value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value.intValue());
    }

}
