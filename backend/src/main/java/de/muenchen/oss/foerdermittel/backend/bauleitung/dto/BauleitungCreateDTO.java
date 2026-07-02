package de.muenchen.oss.foerdermittel.backend.bauleitung.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BauleitungCreateDTO(@NotNull @Min(1) @Max(99) String bauleitung, @NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
