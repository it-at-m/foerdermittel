package de.muenchen.oss.foerdermittel.backend.listenname.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ListennameResponseDTO(@NotNull String id, @NotNull String kurzbez, @NotNull String bezeichnung) {
}
