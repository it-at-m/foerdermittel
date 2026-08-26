package de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto;

import jakarta.validation.constraints.NotNull;

public record UnterabschnittFormContextHauptabschnitt(
        @NotNull String ha,
        @NotNull String bezeichnung) {
}
