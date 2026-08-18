package de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HauptabschnittUpdateDTO(@NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
