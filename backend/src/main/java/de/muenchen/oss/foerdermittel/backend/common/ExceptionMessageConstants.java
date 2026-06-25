package de.muenchen.oss.foerdermittel.backend.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessageConstants {
    public static final String MSG_NOT_FOUND = "Could not find entity with ID %s";
    public static final String MSG_ALREADY_EXISTS = "Entity with ID %s already exists";
}