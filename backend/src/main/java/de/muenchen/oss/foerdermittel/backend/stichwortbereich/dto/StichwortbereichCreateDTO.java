package de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record StichwortbereichCreateDTO(@NotBlank @Size(min = 1, max = 30) @Pattern(regexp = "^[A-Z0-9\\-]+$") String bereich,
        @NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
