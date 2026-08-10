package de.muenchen.oss.foerdermittel.backend.krankenhaus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record KrankenhausUpdateDTO(@NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
