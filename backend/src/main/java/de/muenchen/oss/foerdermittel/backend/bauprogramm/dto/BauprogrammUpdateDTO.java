package de.muenchen.oss.foerdermittel.backend.bauprogramm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BauprogrammUpdateDTO(@NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
