package de.muenchen.oss.foerdermittel.backend.listenname.dto;

import jakarta.validation.constraints.NotNull;

public record ListennameResponseDTO(@NotNull String id, @NotNull String kurzbez, @NotNull String bezeichnung) {
}
