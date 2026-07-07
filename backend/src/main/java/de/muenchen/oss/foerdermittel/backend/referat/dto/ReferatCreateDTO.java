package de.muenchen.oss.foerdermittel.backend.referat.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReferatCreateDTO(@NotNull @Min(0) @Max(99) Integer refnr,
        @NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
