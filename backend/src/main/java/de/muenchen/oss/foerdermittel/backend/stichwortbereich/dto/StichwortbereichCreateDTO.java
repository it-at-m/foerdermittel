package de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record StichwortbereichCreateDTO(@NotNull @Size(min = 1, max = 30) @Pattern(regexp = "^[A-Z0-9\\-]+$") String bereich,
        @NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
