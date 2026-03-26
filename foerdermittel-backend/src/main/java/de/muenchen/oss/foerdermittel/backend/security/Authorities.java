package de.muenchen.oss.foerdermittel.backend.security;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Each possible authority in this project is represented by a constant in this class.
 * The constants are used within the {@link org.springframework.stereotype.Controller} or
 * {@link org.springframework.stereotype.Service} classes in the method security annotations
 * (e.g. {@link PreAuthorize}).
 */
@SuppressWarnings("PMD.DataClass")
public final class Authorities {
    public static final String HAS_ROLE_ADMIN = "hasRole('admin')";
    public static final String HAS_ROLE_SACHBEARBEITUNG = "hasRole('sachbearbeitung')";
    public static final String HAS_ROLE_SACHBEARBEITUNG_HAUSHALT = "hasRole('sachbearbeitunghaushalt')";
    public static final String HAS_ANY_ROLE = "hasAnyRole('admin', 'sachbearbeitung', 'sachbearbeitunghaushalt')";

    private Authorities() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
