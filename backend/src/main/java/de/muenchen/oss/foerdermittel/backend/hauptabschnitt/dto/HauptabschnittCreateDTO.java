package de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HauptabschnittCreateDTO(@NotBlank @Size(min = 1, max = 2) @Pattern(regexp = "^[a-zA-Z0-9]+$") String ha,
        @NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
