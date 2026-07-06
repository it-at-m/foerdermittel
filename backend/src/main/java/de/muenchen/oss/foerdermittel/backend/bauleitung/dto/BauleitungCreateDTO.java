package de.muenchen.oss.foerdermittel.backend.bauleitung.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record BauleitungCreateDTO(@NotNull @Size(min = 1, max = 1) @Pattern(regexp = "^(?:[1-9]|[1-9][0-9])$") String bauleitung,
        @NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
