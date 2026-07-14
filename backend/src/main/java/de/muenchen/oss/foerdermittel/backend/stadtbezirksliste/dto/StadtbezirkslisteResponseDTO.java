package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record StadtbezirkslisteResponseDTO(@NotNull String id, @NotNull String lnaKurzbez, @NotNull BigDecimal bezStadtbezirk, @NotNull String bezeichnung) {
}
