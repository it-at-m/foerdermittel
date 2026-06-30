package de.muenchen.oss.foerdermittel.backend.krankenhaus.dto;

import jakarta.validation.constraints.NotNull;

public record KrankenhausResponseDTO(@NotNull String id, @NotNull String krhname, @NotNull String bezeichnung) {
}

