package de.muenchen.oss.foerdermittel.backend.common;

import org.mapstruct.Named;

import java.math.BigDecimal;

public class NumberMapper {

    @Named("bigDecimalToIntegerString")
    public static String bigDecimalToIntegerString(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value.intValue());
    }

}
