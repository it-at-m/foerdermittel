package de.muenchen.oss.foerdermittel.backend.traeger.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TraegerUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
