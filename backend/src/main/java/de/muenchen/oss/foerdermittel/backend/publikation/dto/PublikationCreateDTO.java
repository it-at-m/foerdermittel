package de.muenchen.oss.foerdermittel.backend.publikation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PublikationCreateDTO(@NotNull @Size(min = 1, max = 1) @Pattern(regexp = "^[A-Z]+$") String kurzform,
                                   @NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
