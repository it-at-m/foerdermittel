package de.muenchen.oss.foerdermittel.backend.common;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.mapstruct.Named;

public final class TimeZoneMapper {

    private TimeZoneMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    @Named("offSetDateTimeToLocalDate")
    public static LocalDate map(final OffsetDateTime value) {
        return value == null
                ? null
                : value.atZoneSameInstant(ZoneId.systemDefault()).toLocalDate();
    }

    @Named("localDateToOffSetDate")
    public static OffsetDateTime map(final LocalDate value) {
        return value == null
                ? null
                : value.atStartOfDay().atOffset(ZoneOffset.UTC);
    }

}
