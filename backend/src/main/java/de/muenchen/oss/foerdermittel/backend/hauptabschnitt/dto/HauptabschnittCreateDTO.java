package de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HauptabschnittCreateDTO(@NotNull @Size(min = 1, max = 2) @Pattern(regexp = "^[a-zA-Z0-9]+$") String ha,
                                   @NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
