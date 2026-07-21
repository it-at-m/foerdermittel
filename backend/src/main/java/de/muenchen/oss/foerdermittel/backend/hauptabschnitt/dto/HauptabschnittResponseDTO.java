package de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto;

import jakarta.validation.constraints.NotNull;

public record HauptabschnittResponseDTO(@NotNull String id, @NotNull String ha, @NotNull String bezeichnung) {
}
