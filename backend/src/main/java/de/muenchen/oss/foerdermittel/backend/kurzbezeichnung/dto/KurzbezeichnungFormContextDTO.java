package de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto;

import jakarta.validation.constraints.NotNull;

/// @param kurzbez
/// @param bezeichnung
public record KurzbezeichnungFormContextDTO(
        @NotNull String kurzbez,
        @NotNull String bezeichnung) {
}
