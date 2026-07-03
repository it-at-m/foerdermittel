package de.muenchen.oss.foerdermittel.backend.util;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;

import java.util.Optional;

public class ServiceUtils {

    public static <T, ID> T getEntityOrThrowException(ID id, Optional<T> entityOptional, String messageFormat, Object... args) {
        return entityOptional.orElseThrow(() -> new NotFoundException(String.format(messageFormat, args)));
    }
}
