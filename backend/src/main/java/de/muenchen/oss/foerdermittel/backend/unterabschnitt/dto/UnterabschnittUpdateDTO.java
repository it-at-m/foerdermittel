package de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UnterabschnittUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
