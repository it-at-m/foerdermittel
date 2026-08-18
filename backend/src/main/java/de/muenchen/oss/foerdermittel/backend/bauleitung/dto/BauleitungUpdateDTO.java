package de.muenchen.oss.foerdermittel.backend.bauleitung.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BauleitungUpdateDTO(@NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
