package de.muenchen.oss.foerdermittel.backend.krankenhaus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record KrankenhausCreateDTO(@NotBlank @Size(min = 1, max = 1) @Pattern(regexp = "^[A-Z]+$") String krhname,
        @NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
