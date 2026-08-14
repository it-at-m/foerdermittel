package de.muenchen.oss.foerdermittel.backend.traeger.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TraegerUpdateDTO(@NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
