package de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HauptabschnittUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung) {
}

