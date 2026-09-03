package de.muenchen.oss.foerdermittel.backend.common;

/** Exception if data cannot be found. */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class NotFoundException extends RuntimeException {
    /**
     *
     * @param targetClass entity class that was requested
     * @param id id of the entity
     */
    public <I> NotFoundException(final Class<?> targetClass, final I id) {
        super(String.format("The %s with ID %s was not found.", targetClass.getSimpleName(), id));
    }
}
