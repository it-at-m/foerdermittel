package de.muenchen.oss.foerdermittel.backend.security;

/// Each possible authority in this project is represented by a constant in this class.
///
/// The constants are used within the [org.springframework.stereotype.Controller] or
/// [org.springframework.stereotype.Service] classes in the method security annotations (e.g.
/// [org.springframework.security.access.prepost.PreAuthorize]).
public final class Authorities {
    public static final String HAS_ROLE_ADMIN = "hasAnyRole('admin')";
    public static final String HAS_ROLE_SACHBEARBEITUNG_HAUSHALT = "hasAnyRole('sachbearbeitunghaushalt', 'admin')";
    public static final String HAS_ANY_ROLE = "hasAnyRole('admin', 'sachbearbeitung', 'sachbearbeitunghaushalt')";

    private Authorities() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
