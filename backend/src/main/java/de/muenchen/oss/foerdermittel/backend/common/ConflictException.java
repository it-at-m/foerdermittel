package de.muenchen.oss.foerdermittel.backend.common;

/// Exception if a request conflicts with already stored data, e.g. a business uniqueness rule.
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class ConflictException extends RuntimeException {
    public ConflictException(final String message) {
        super(message);
    }
}
