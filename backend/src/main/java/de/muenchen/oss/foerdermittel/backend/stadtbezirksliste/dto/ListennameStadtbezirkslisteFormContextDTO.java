package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import jakarta.validation.constraints.NotNull;


/// @param kurzbez
/// @param bezeichnung
public record ListennameStadtbezirkslisteFormContextDTO(
        @NotNull String kurzbez,
        @NotNull String bezeichnung) {
}
