package de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for Stichwortbereich to be used in other FormContexts other than its own.
 *
 * @param bereich
 * @param bezeichnung
 */
public record StichwortbereichFormContextDTO(
        @NotNull String bereich,
        @NotNull String bezeichnung) {
}
