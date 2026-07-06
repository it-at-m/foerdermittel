package de.muenchen.oss.foerdermittel.backend.traeger.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TraegerCreateDTO(@NotNull @Min(1) @Max(99) Integer kurzform, @NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
