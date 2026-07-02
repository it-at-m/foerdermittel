package de.muenchen.oss.foerdermittel.backend.krankenhaus.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record KrankenhausUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
