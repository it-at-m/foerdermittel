package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record StadtbezirkslisteResponseDTO(@NotNull String id, @NotNull String lna_kurzbez, @NotNull BigDecimal bez_stadtbezirk, @NotNull String bezeichnung) {
}
