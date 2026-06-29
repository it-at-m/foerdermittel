package de.muenchen.oss.foerdermittel.backend.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Exception if data is malformed. */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class BadRequestException extends ResponseStatusException {
    /**
     * BadRequestException constructor
     *
     * @param message Exception message
     */
    public BadRequestException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
