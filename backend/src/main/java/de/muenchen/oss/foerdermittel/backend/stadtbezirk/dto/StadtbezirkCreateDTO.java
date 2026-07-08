package de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StadtbezirkCreateDTO(@NotNull @Min(0) @Max(99) Integer stadtbezirk, @NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
