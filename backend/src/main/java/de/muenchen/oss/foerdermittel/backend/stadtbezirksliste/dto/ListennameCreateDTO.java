package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import jakarta.validation.constraints.*;

public record ListennameCreateDTO(@NotNull @Size(min = 1, max = 3) @Pattern(regexp = "^[A-Z0-9]+$") String kurzbez, @NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
