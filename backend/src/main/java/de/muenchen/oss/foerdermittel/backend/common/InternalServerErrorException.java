package de.muenchen.oss.foerdermittel.backend.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Exception for generous internal server errors */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class InternalServerErrorException extends ResponseStatusException {
    /**
     * InternalServerErrorException constructor
     *
     * @param message Exception message
     */
    public InternalServerErrorException(final String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
