package de.muenchen.oss.foerdermittel.backend.bauleitung.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record BauleitungCreateDTO(@NotBlank @Size(min = 1, max = 1) @Pattern(regexp = "^[A-Z0-9]+$") String bauleitung,
        @NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
