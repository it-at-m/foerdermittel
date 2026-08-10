package de.muenchen.oss.foerdermittel.backend.publikation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PublikationUpdateDTO(@NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
