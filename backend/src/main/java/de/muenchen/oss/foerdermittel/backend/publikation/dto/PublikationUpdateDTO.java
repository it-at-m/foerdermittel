package de.muenchen.oss.foerdermittel.backend.publikation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PublikationUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
