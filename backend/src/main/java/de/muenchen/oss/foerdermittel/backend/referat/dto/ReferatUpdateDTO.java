package de.muenchen.oss.foerdermittel.backend.referat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReferatUpdateDTO(@NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
