package de.muenchen.oss.foerdermittel.backend.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Exception if data cannot be found. */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class AlreadyExistsException extends ResponseStatusException {
    /**
     * NotFoundException constructor
     *
     * @param message Exception message
     */
    public AlreadyExistsException(final String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
