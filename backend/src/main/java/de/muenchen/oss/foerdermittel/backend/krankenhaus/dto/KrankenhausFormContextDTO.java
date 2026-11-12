package de.muenchen.oss.foerdermittel.backend.krankenhaus.dto;

import jakarta.validation.constraints.NotNull;

/// @param krhname
/// @param bezeichnung

public record KrankenhausFormContextDTO(
        @NotNull String krhname,
        @NotNull String bezeichnung) {

}
