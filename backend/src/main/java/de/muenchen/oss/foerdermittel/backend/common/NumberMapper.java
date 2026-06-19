package de.muenchen.oss.foerdermittel.backend.common;

import java.math.BigDecimal;
import org.mapstruct.Named;

public class NumberMapper {

    @Named("bigDecimalToIntegerString")
    public static String bigDecimalToIntegerString(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value.intValue());
    }

}
