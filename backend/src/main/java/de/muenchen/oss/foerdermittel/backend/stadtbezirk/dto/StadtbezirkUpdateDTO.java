package de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StadtbezirkUpdateDTO(@NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
