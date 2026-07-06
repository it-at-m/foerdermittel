package de.muenchen.oss.foerdermittel.backend.util;

import static de.muenchen.oss.foerdermittel.backend.common.ExceptionMessageConstants.MSG_NOT_FOUND;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import org.springframework.data.repository.CrudRepository;

public final class ServiceUtils {

    private ServiceUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static <T, I> T getEntityOrThrowNotFoundException(final I id, final CrudRepository<T, I> repository) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(String.format(MSG_NOT_FOUND, id)));
    }
}
