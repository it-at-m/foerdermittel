package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record StadtbezirkslisteCreateDTO(@NotNull @Size(min = 1, max = 3) @Pattern(regexp = "^[A-Z0-9]+$") String lna_kurzbez,
                                         @NotNull @Size(min = 0, max = 99) BigDecimal bez_stadtbezirk,
                                         @NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
