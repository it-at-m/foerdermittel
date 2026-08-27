package de.muenchen.oss.foerdermittel.backend.util;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import org.springframework.data.repository.CrudRepository;

public final class ServiceUtils {

    private ServiceUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static <T, I, C> T getEntityOrThrowNotFoundException(final I id, final CrudRepository<T, I> repository, final Class<C> entityClass) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(entityClass, id));
    }

    public static <T, I, C> void checkExistsOrThrowNotFoundException(final I id, final CrudRepository<T, I> repository, final Class<C> entityClass) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(entityClass, id);
        }
    }
}
