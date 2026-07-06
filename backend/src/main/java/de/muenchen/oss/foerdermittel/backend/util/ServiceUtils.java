package de.muenchen.oss.foerdermittel.backend.util;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;

import java.util.Optional;

public final class ServiceUtils {

    private ServiceUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static <T, I> T getEntityOrThrowException(final I id, final Optional<T> entityOptional, final String messageFormat) {
        return entityOptional.orElseThrow(() -> new NotFoundException(String.format(messageFormat, id)));
    }
}
