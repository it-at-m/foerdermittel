package de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StadtbezirkUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
