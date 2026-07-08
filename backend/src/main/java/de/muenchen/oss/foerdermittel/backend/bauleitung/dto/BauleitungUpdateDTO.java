package de.muenchen.oss.foerdermittel.backend.bauleitung.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BauleitungUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
