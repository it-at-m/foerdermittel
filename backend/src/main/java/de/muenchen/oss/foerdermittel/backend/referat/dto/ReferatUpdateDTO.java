package de.muenchen.oss.foerdermittel.backend.referat.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReferatUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
