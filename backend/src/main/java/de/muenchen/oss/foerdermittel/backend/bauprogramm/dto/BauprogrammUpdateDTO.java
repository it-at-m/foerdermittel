package de.muenchen.oss.foerdermittel.backend.bauprogramm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BauprogrammUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
